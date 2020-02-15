package parkinson.audio.server.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import parkinson.audio.server.analyzer.Parkinson;
import parkinson.audio.server.database.DatabaseSurveys;
import parkinson.audio.server.utils.ConstantsFCR;

@RestController
public class AudioServlet {

	private static final String SAVE_DIR = "/audio_uploads/";

	Logger log = LoggerFactory.getLogger(AudioServlet.class);

	@Autowired
	DatabaseSurveys db;

	@PostMapping(path = SAVE_DIR)
	public String submit(@RequestParam("files") MultipartFile[] files) throws IllegalStateException, IOException {
		JSONObject jsonResp = new JSONObject();

		log.info("Files received: size:" + files.length);

		if (files.length != 6) {
			log.error("Number files received is wrong then needed . . .");
			jsonResp.put("errors", "Need 6 files.");
			return jsonResp.toString();
		}

		log.debug("Convert files to be managed . . .");
		File A1 = new File("filename1");
		files[0].transferTo(A1);
		File A2 = new File("filename2");
		files[1].transferTo(A2);

		File I1 = new File("filename3");
		files[2].transferTo(I1);
		File I2 = new File("filename4");
		files[3].transferTo(I2);

		File U1 = new File("filename5");
		files[4].transferTo(U1);
		File U2 = new File("filename6");
		files[5].transferTo(U2);

		log.debug("Start manage files . . . ");
		Map<String, Double> results = new Parkinson(A1, A2, I1, I2, U1, U2).analyze();

		jsonResp.put("upload", "done").put("fcr1", results.get(ConstantsFCR.FCR_1_SESSION)).put("fcr2",
				results.get(ConstantsFCR.FCR_2_SESSION));
		return jsonResp.toString();
	}

	@GetMapping("/db")
	public String insert(@RequestParam(ConstantsFCR.FCR_1_SESSION) Double fcr1,
			@RequestParam(ConstantsFCR.FCR_2_SESSION) Double fcr2) {
		JSONObject jsonResp = new JSONObject();
		if (db.insertRilevazioni(fcr1, fcr2) < 1) {
			log.error("Not save datas on db . . .");
			return jsonResp.put("error", "Not save survays . . .").toString();
		}
		return jsonResp.put("success", "Save survays . . .").toString();

	}

}
