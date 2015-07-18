package com.sng.bpel.main.param;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * User: mikola
 * Date: 07.05.15
 * Time: 16:34
 */
public class AppArgumentReader {

    private final List<String> allArgs;
    private final List<Option> options;
    private final List<? extends MandatoryParameter> parameters;
    private final String appConsoleName;
    private final boolean ignoreUnknownArgs;
    private final boolean printToConsole;

    public AppArgumentReader(String applicationConsoleName, String[] args,
                             List<? extends MandatoryParameter> parameters, Option... options) {
        this(applicationConsoleName, false, args, parameters, options);
    }

    public AppArgumentReader(String appConsoleName, boolean ignoreUnknownArgs, String[] args,
                             List<? extends MandatoryParameter> parameters, Option... options) {
        this(true, appConsoleName, ignoreUnknownArgs, args, parameters, options);
    }

    public AppArgumentReader(boolean printToConsole, String appConsoleName, boolean ignoreUnknownArgs, String[] args,
                             List<? extends MandatoryParameter> parameters, Option... options) {
        this.printToConsole = printToConsole;
        this.appConsoleName = appConsoleName != null ? appConsoleName : "";
        this.ignoreUnknownArgs = ignoreUnknownArgs;
        this.parameters = parameters != null ? parameters : new ArrayList<MandatoryParameter>();
        this.allArgs = args != null ? new ArrayList<>(Arrays.asList(args)) : new ArrayList<String>();
        this.options = options != null ? Arrays.asList(options) : new ArrayList<Option>();
    }


    public boolean parse() throws AppArgumentReaderException {
        if (needHelp()) {
            if (printToConsole) {
                printUsage();
                return false;
            } else {
                throw new UserNeedsHelpException();
            }
        }

        if (allArgs.size() < this.parameters.size()) {
            return error("Not enough arguments");
        }

        List<String> processedArguments = new ArrayList<>();
        Iterator<String> it = allArgs.iterator();

        // process options
        while (it.hasNext()) {
            String arg = it.next();
            for (Option op : options) {
                if (arg.equals(op.getName())) {
                    op.setExisted(true);

                    if (op instanceof AbstractValuedOption) {
                        AbstractValuedOption valuedOption = (AbstractValuedOption) op;
                        if (it.hasNext()) {
                            String optionsValue = it.next();
                            processedArguments.add(optionsValue);
                            try {
                                valuedOption.setRawValue(optionsValue);
                            } catch (Throwable e) {
                                return error("Error reading value '" + optionsValue + "' for the "
                                        + op.getName() + " option:" + e);
                            }
                        } else {
                            return error("Missing value for the " + op.getName() + " option");
                        }
                    }

                    processedArguments.add(arg);
                }
            }
        }

        // process mandatory parameters
        allArgs.removeAll(processedArguments);
        if (allArgs.size() < parameters.size()) {
            return error("Not enough parameters");
        }
        if (!ignoreUnknownArgs && allArgs.size() > parameters.size()) {
            return error("Unknown argument: " + allArgs.get(parameters.size()));
        }

        for (int i = 0; i < parameters.size(); i++) {
            try {
                parameters.get(i).setRawValue(allArgs.get(i));
            } catch (Throwable e) {
                return error("Error reading value '" + allArgs.get(i) + "' of the "
                        + (i + 1) + " parameter:" + e);
            }
        }

        return true;
    }

    private boolean needHelp() {
        for (String a : allArgs) {
            if (a.equals("-h") || a.equals("--help") || a.equals("-?")) {
                return true;
            }
        }
        return false;
    }

    public boolean error(String s) throws AppArgumentReaderException {
        if (printToConsole) {
            System.out.println(s);
            printUsage();
            return false;
        }
        throw new AppArgumentReaderException(s);
    }

    public void printUsage() {
        System.out.println(usage());
    }

    public String usage() {
        //java -jar stomo.jar
        StringBuilder sb = new StringBuilder();
        sb.append("Usage:\n\t").append(appConsoleName);
        for (MandatoryParameter param : parameters) {
            sb.append(" <").append(param.getShortDesc()).append(">");
        }

        sb.append(" [");
        for (Option op : options) {
            sb.append(" ").append(op.getName());
            if (op instanceof AbstractValuedOption) {
                AbstractValuedOption avo = (AbstractValuedOption) op;
                sb.append(" <").append(avo.getValueShortDesc()).append(">");
            }
        }
        sb.append(" -help");
        sb.append("]");

        return sb.toString();
    }
}
