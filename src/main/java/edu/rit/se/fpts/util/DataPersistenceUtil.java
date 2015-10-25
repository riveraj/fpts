package edu.rit.se.fpts.util;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import edu.rit.se.fpts.Main;
import edu.rit.se.fpts.model.Model;

public class DataPersistenceUtil {

	public static enum Mode {
		XML, CSV;
	}

	public static Model getModelFromDataFile() {
		try {
			File file = Paths.get(Main.class.getClassLoader().getResource("data.xml").toURI()).toFile();
			Mode mode = DataPersistenceUtil.Mode.XML;
			return (Model) DataPersistenceUtil.deserialize(Model.class, file, mode);
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void saveModelToDataFile(Model model) {
		try {
			File file = Paths.get(Main.class.getClassLoader().getResource("data.xml").toURI()).toFile();
			Mode mode = DataPersistenceUtil.Mode.XML;
			DataPersistenceUtil.serialize(model, file, mode);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	public static void serialize(Object object, File file, Mode mode) {
		switch (mode) {
		case XML:
			try {
				JAXBContext context = JAXBContext.newInstance(object.getClass());
				Marshaller m = context.createMarshaller();
				m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
				m.marshal(object, file);
				break;
			} catch (JAXBException e) {
				e.printStackTrace();
			}
		case CSV:
			// Not implemented
			break;
		default:
			throw new IllegalArgumentException();
		}
	}

	public static Object deserialize(Class<?> clazz, File file, Mode mode) {
		switch (mode) {
		case XML:
			try {
				JAXBContext context = JAXBContext.newInstance(clazz);
				Unmarshaller um = context.createUnmarshaller();
				return um.unmarshal(file);
			} catch (JAXBException e) {
				e.printStackTrace();
				return null;
			}
		case CSV:
			// Not implemented
			return null;
		default:
			throw new IllegalArgumentException();
		}
	}
}
