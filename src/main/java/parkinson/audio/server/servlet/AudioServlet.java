package parkinson.audio.server.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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

	@RequestMapping(path = SAVE_DIR, method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String submit(MultipartFile fileA1, MultipartFile fileA2, MultipartFile fileI1, MultipartFile fileI2,
			MultipartFile fileU1, MultipartFile fileU2) throws IllegalStateException, IOException {
		JSONObject jsonResp = new JSONObject();
		log.info("Invoke mehtod " + SAVE_DIR);

		if (validate(jsonResp, fileA1, "fileA1") | validate(jsonResp, fileA2, "fileA2")
				| validate(jsonResp, fileI1, "fileI1") | validate(jsonResp, fileI2, "fileI2")
				| validate(jsonResp, fileU1, "fileU1") | validate(jsonResp, fileU2, "fileU2")) {
			jsonResp.put("error", "Need 6 files.");
			return jsonResp.toString();
		}

		log.debug("Convert files to be managed . . .");
		File A1 = new File("filename1");
		fileA1.transferTo(A1);
		File A2 = new File("filename2");
		fileA2.transferTo(A2);

		File I1 = new File("filename3");
		fileI1.transferTo(I1);
		File I2 = new File("filename4");
		fileI2.transferTo(I2);

		File U1 = new File("filename5");
		fileU1.transferTo(U1);
		File U2 = new File("filename6");
		fileU2.transferTo(U2);

		log.debug("Start manage files . . . ");
		Map<String, Double> results = new Parkinson(A1, A2, I1, I2, U1, U2).analyze();

		jsonResp.put("upload", "done").put("fcr1", results.get(ConstantsFCR.FCR_1_SESSION)).put("fcr2",
				results.get(ConstantsFCR.FCR_2_SESSION));
		return jsonResp.toString();
	}

	private boolean validate(JSONObject jsonResp, MultipartFile generic_file, String nameFile) {
		if (generic_file == null) {
			jsonResp.put(nameFile, "null value");
			return true;
		}
		return false;
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
