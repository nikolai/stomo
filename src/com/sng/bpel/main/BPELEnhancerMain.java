package com.sng.bpel.main;

import com.sm.bpelenhancer.ChangeLog;
import com.sm.bpelenhancer.EnhancerChain;
import com.sm.bpelenhancer.sertopar.Ser2ParEnhancer;
import com.sm.logging.LogService;
import com.sm.util.XmlUtil;
import com.sng.bpel.main.param.AppArgumentReader;
import com.sng.bpel.main.param.AppArgumentReaderException;
import com.sng.bpel.main.param.MandatoryParameter;
import com.sng.bpel.main.param.StringParameter;
import org.oasis_open.docs.wsbpel._2_0.process.executable.ObjectFactory;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TProcess;

import java.io.File;
import java.util.Arrays;
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
        enhancerChain.addEnhancer(new Ser2ParEnhancer());
    }

    public static void main(String[] args) throws AppArgumentReaderException {
        AppArgumentReader params = new AppArgumentReader("java -jar bpel-enhancer.jar", args, Arrays.asList(BPEL_FILE));
        if (!params.parse()) {
            System.exit(0);
        }

        String bpelFilePath = BPEL_FILE.getValue();
        File bpelFile = new File(bpelFilePath);

        log.info("Reading BPEL file " + bpelFile.getAbsolutePath() + "...");
        try {
            TProcess process = XmlUtil.unmarshall(bpelFile.getAbsolutePath(), TProcess.class, ObjectFactory.class);
            log.info("Running enhancer against business process '" + process.getName() + "'");
            ChangeLog changeLog = enhancerChain.start(process);
            log.info("Enhancing changelog:\n" + changeLog);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Cannot create JAXB model of the BPEL file", e);

        }


//        ChildActivitySelector.SelectedChild child = ChildActivitySelector.getOne().selectChild(process);
//        TSequence seq = (TSequence) child.getActivity();
//        enhancerChain.start();
    }

}
