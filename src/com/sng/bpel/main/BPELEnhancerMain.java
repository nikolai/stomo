package com.sng.bpel.main;

import com.sm.bpelenhancer.ChangeLog;
import com.sm.bpelenhancer.EnhancerChain;
import com.sm.bpelenhancer.sertopar.S2PEnhancer;
import com.sm.logging.LogService;
import com.sm.util.XmlUtil;
import com.sng.bpel.main.param.AppArgumentReaderException;
import com.sng.bpel.main.param.MandatoryParameter;
import com.sng.bpel.main.param.StringParameter;
import org.oasis_open.docs.wsbpel._2_0.process.executable.ObjectFactory;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TProcess;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * User: mikola
 * Date: 07.05.15
 * Time: 16:33
 */
public class BPELEnhancerMain {
    private static final EnhancerChain enhancerChain = new EnhancerChain();
    private static StringParameter BPEL_FILE = MandatoryParameter.createStringParameter("bpelFile");
    private static Logger log = LogService.get();

    static {
        enhancerChain.addEnhancer(new S2PEnhancer());
    }

    public static void main(String[] args) throws AppArgumentReaderException {
//        AppArgumentReader params = new AppArgumentReader("java -jar bpel-enhancer.jar", args, Collections.singletonList(BPEL_FILE));
//        if (!params.parse()) {
//            System.exit(0);
//        }
        List<Exception> errors = new ArrayList<>();

//        count
        for (String arg : args) {
            try {
                processFile(new File(arg));
            } catch (Exception e) {
                log.log(Level.SEVERE, "Error occurs: ", e);
                System.out.println("error: " + e);
                errors.add(e);
            }
        }
        System.out.println("Errors: " + errors.size());
//
//        String bpelFilePath = BPEL_FILE.getValue();
//        if (bpelFilePath.contains("*")) {
//            String path = bpelFilePath.substring(0, bpelFilePath.indexOf("*") - 1);
//            File dir = new File(path);
//            File[] files = dir.listFiles();
//            for (File file : files) {
//                processFile(file);
//            }
//        } else {
//            processFile(new File(bpelFilePath));
//        }

    }

    private static void processFile(File bpelFile) throws Exception {

        log.info("Reading BPEL file " + bpelFile.getAbsolutePath() + "...");
        try {
            TProcess process = XmlUtil.unmarshall(bpelFile.getAbsolutePath(), TProcess.class, ObjectFactory.class);
            log.info("Running enhancer against business process '" + process.getName() + "'");
            ChangeLog changeLog = enhancerChain.start(process);
            log.info("Enhancing changelog:\n" + changeLog);

            ObjectFactory factory = new ObjectFactory();
            if (!changeLog.isEmpty()) {
                String newName = bpelFile.getAbsolutePath().replaceFirst("\\.bpel", "_enhanced\\.bpel");
                File enhancedFile = new File(newName);
                log.info("Saving changes to BPEL file " + enhancedFile.getAbsolutePath() + "...");
                XmlUtil.marshall(newName, factory.createProcess(process), ObjectFactory.class);

                XmlAliasSaver xas = new XmlAliasSaver(bpelFile, enhancedFile);
                xas.patch();
            }

        } catch (Exception e) {
            throw e;
        }
    }

}
