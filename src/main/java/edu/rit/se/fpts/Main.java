package edu.rit.se.fpts;

import java.util.ArrayList;

import javax.xml.bind.JAXBException;

import edu.rit.se.fpts.controller.LoginManager;
import edu.rit.se.fpts.controller.Manager;
import edu.rit.se.fpts.model.Model;
import edu.rit.se.fpts.model.User;
import edu.rit.se.fpts.util.DataPersistenceUtil;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		Model model = DataPersistenceUtil.getModelFromDataFile();

		if (model == null)
			model = buildNewModel();

		if (getParameters().getRaw().get(0).equals("-delete"))
			deleteUser(model, getParameters().getRaw().get(1));

		Manager manager = new LoginManager(primaryStage, model);
		manager.init();
	}

	public static void main(String[] args) throws JAXBException {
		launch(args);
	}

	private Model buildNewModel() {
		Model model = new Model();
		model.setUsers(new ArrayList<User>());
		DataPersistenceUtil.saveModelToDataFile(model);
		return model;
	}

	private boolean deleteUser(Model model, String loginId) {
		for (int i = 0; i < model.getUsers().size(); i++)
			if (model.getUsers().get(i).getLoginId().equals(loginId)) {
				model.getUsers().remove(i);
				return true;
			}

		return false;
	}
}
