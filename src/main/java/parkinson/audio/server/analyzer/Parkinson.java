package parkinson.audio.server.analyzer;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ca.hedlund.jpraat.binding.fon.Formant;
import ca.hedlund.jpraat.binding.fon.Sound;
import ca.hedlund.jpraat.binding.stat.Table;
import ca.hedlund.jpraat.exceptions.PraatException;
import parkinson.audio.server.database.DatabaseSurveys;
import parkinson.audio.server.utils.ConstantsFCR;

public class Parkinson {

	private File fileA1;
	private File fileA2;
	private File fileI1;
	private File fileI2;
	private File fileU1;
	private File fileU2;

	@Autowired
	DatabaseSurveys db;

	Logger log = LoggerFactory.getLogger(Parkinson.class);

	public Parkinson(File fileA1, File fileA2, File fileI1, File fileI2, File fileU1, File fileU2) {
		this.fileA1 = fileA1;
		this.fileA2 = fileA2;
		this.fileI1 = fileI1;
		this.fileI2 = fileI2;
		this.fileU1 = fileU1;
		this.fileU2 = fileU2;
	}

	public Map<String, Double> analyze() {
		log.info("Managing datas . . .");
		Map<String, Double> results = new HashMap<>();
		double f1A1 = 0, f2A1 = 0, f1A2 = 0, f2A2 = 0, f1I1 = 0, f2I1 = 0, f1I2 = 0, f2I2 = 0, f1U1 = 0, f2U1 = 0,
				f1U2 = 0, f2U2 = 0;
		double avgf1A1, avgf2A1, avgf1A2, avgf2A2, avgf1I1, avgf2I1, avgf1I2, avgf2I2, avgf1U1, avgf2U1, avgf1U2,
				avgf2U2;

		try {
			// creazione sound da praat
			Sound a1 = Sound.readFromPath(fileA1.getPath());
			Sound a2 = Sound.readFromPath(fileA2.getPath());
			Sound i1 = Sound.readFromPath(fileI1.getPath());
			Sound i2 = Sound.readFromPath(fileI2.getPath());
			Sound u1 = Sound.readFromPath(fileU1.getPath());
			Sound u2 = Sound.readFromPath(fileU2.getPath());

			List<IndexValue> indexA1 = new ArrayList<>();
			List<IndexValue> indexA2 = new ArrayList<>();
			List<IndexValue> indexI1 = new ArrayList<>();
			List<IndexValue> indexI2 = new ArrayList<>();
			List<IndexValue> indexU1 = new ArrayList<>();
			List<IndexValue> indexU2 = new ArrayList<>();

			// creazione formanti
			Formant fA1 = createFormant(a1);
			Formant fA2 = createFormant(a2);
			Formant fI1 = createFormant(i1);
			Formant fI2 = createFormant(i2);
			Formant fU1 = createFormant(u1);
			Formant fU2 = createFormant(u2);

			// creazione tabelle
			Table tabA1 = createTable(fA1);
			Table tabA2 = createTable(fA2);
			Table tabI1 = createTable(fI1);
			Table tabI2 = createTable(fI2);
			Table tabU1 = createTable(fU1);
			Table tabU2 = createTable(fU2);

			// creazione liste con valori formanti f1 f2 delle registrazioni della prima
			// sessione di esercizi
			for (int row = 1; row <= tabA1.getNrow(); row++) {
				indexA1.add(new IndexValue(tabA1.getNumericValue(row, 1), tabA1.getNumericValue(row, 4),
						tabA1.getNumericValue(row, 5), tabA1.getNumericValue(row, 2)));
			}
			for (int row = 1; row <= tabI1.getNrow(); row++) {
				indexI1.add(new IndexValue(tabI1.getNumericValue(row, 1), tabI1.getNumericValue(row, 4),
						tabI1.getNumericValue(row, 5), tabI1.getNumericValue(row, 2)));
			}
			for (int row = 1; row <= tabU1.getNrow(); row++) {
				indexU1.add(new IndexValue(tabU1.getNumericValue(row, 1), tabU1.getNumericValue(row, 4),
						tabU1.getNumericValue(row, 5), tabU1.getNumericValue(row, 2)));
			}
			// creazione liste con valori formanti f1 f2 delle registrazioni della seconda
			// sessione di esercizi
			for (int row = 1; row <= tabA2.getNrow(); row++) {
				indexA2.add(new IndexValue(tabA2.getNumericValue(row, 1), tabA2.getNumericValue(row, 4),
						tabA2.getNumericValue(row, 5), tabA2.getNumericValue(row, 2)));
			}
			for (int row = 1; row <= tabI2.getNrow(); row++) {
				indexI2.add(new IndexValue(tabI2.getNumericValue(row, 1), tabI2.getNumericValue(row, 4),
						tabI2.getNumericValue(row, 5), tabI2.getNumericValue(row, 2)));
			}
			for (int row = 1; row <= tabU2.getNrow(); row++) {
				indexU2.add(new IndexValue(tabU2.getNumericValue(row, 1), tabU2.getNumericValue(row, 4),
						tabU2.getNumericValue(row, 5), tabU2.getNumericValue(row, 2)));
			}

			for (IndexValue indexValue : indexA1) {
				f1A1 += indexValue.getF1();
				f2A1 += indexValue.getF2();
			}
			avgf1A1 = f1A1 / indexA1.size();
			avgf2A1 = f2A1 / indexA1.size();

			for (IndexValue indexValue : indexA2) {
				f1A2 += indexValue.getF1();
				f2A2 += indexValue.getF2();
			}
			avgf1A2 = f1A2 / indexA2.size();
			avgf2A2 = f2A2 / indexA2.size();

			for (IndexValue indexValue : indexI1) {
				f1I1 += indexValue.getF1();
				f2I1 += indexValue.getF2();
			}
			avgf1I1 = f1I1 / indexI1.size();
			avgf2I1 = f2I1 / indexI1.size();

			for (IndexValue indexValue : indexI2) {
				f1I2 += indexValue.getF1();
				f2I2 += indexValue.getF2();
			}
			avgf1I2 = f1I2 / indexI2.size();
			avgf2I2 = f2I2 / indexI2.size();

			for (IndexValue indexValue : indexU1) {
				f1U1 += indexValue.getF1();
				f2U1 += indexValue.getF2();
			}
			avgf1U1 = f1U1 / indexU1.size();
			avgf2U1 = f2U1 / indexU1.size();

			for (IndexValue indexValue : indexU2) {
				f1U2 += indexValue.getF1();
				f2U2 += indexValue.getF2();
			}
			avgf1U2 = f1U2 / indexU2.size();
			avgf2U2 = f2U2 / indexU2.size();

			// primo
			Double fcr1 = (avgf1I1 + avgf1U1 + avgf2U1 + avgf2A1) / (avgf2I1 + avgf1A1);

			results.put(ConstantsFCR.FCR_1_SESSION, fcr1);

			// secondo
			Double fcr2 = (avgf1I2 + avgf1U2 + avgf2U2 + avgf2A2) / (avgf2I2 + avgf1A2);

			results.put(ConstantsFCR.FCR_2_SESSION, fcr2);
			if (db.insertRilevazioni(fcr1, fcr2) < 1) {
				log.error("Not save datas on db . . .");
			}
		} catch (PraatException e) {
			log.error("error on manage datas :" + e.getMessage());

		}

		return results;
	}

	private Formant createFormant(Sound sound) throws PraatException {
		return sound.to_Formant_burg(0.20, 3, 5000, 0.10, 50);
	}

	private Table createTable(Formant formant) throws PraatException {
		return formant.downto_Table(false, true, 2, true, 2, true, 2, false);
	}

}
