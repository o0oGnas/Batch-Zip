package xyz.gnas.piz.app.common;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.media.Media;
import xyz.gnas.piz.app.FXMain;

/**
 * @author Gnas
 * @Description Manage resources, including lazy load and caching
 * @Date Oct 10, 2018
 */
public class ResourceManager {
	private static final String CSS_FOLDER = "css/";
	private static final String ICON_FOLDER = "icons/";
	private static final String FXML_FOLDER = "fxml/";
	private static final String ZIP_FXML_FOLDER = FXML_FOLDER + "zip/";

	private static Image appIcon;

	private static Media notificationSound;

	private static List<String> cssList;

	private static URL appFXML;
	private static URL zipFXML;
	private static URL zipItemFXML;
	private static URL referenceFXML;

	public static Image getAppIcon() {
		if (appIcon == null) {
			appIcon = new Image(FXMain.class.getClassLoader().getResourceAsStream(ICON_FOLDER + "app.png"));
		}

		return appIcon;
	}

	public static Media getNotificationSound() {
		if (notificationSound == null) {
			notificationSound = new Media(
					FXMain.class.getClassLoader().getResource("notification.wav").toExternalForm());
		}

		return notificationSound;
	}

	public static List<String> getCSSList() {
		if (cssList == null) {
			cssList = new LinkedList<String>();
			cssList.add(FXMain.class.getClassLoader().getResource(CSS_FOLDER + "app.css").toExternalForm());
			cssList.add(FXMain.class.getClassLoader().getResource(CSS_FOLDER + "theme.css").toExternalForm());
		}

		return cssList;
	}

	public static URL getAppFXML() {
		if (appFXML == null) {
			appFXML = FXMain.class.getClassLoader().getResource(FXML_FOLDER + "App.fxml");
		}

		return appFXML;
	}

	public static URL getZipFXML() {
		if (zipFXML == null) {
			zipFXML = FXMain.class.getClassLoader().getResource(ZIP_FXML_FOLDER + "Zip.fxml");
		}

		return zipFXML;
	}

	public static URL getZipItemFXML() {
		if (zipItemFXML == null) {
			zipItemFXML = FXMain.class.getClassLoader().getResource(ZIP_FXML_FOLDER + "ZipItem.fxml");
		}

		return zipItemFXML;
	}

	public static URL getReferenceFXML() {
		if (referenceFXML == null) {
			referenceFXML = FXMain.class.getClassLoader().getResource(FXML_FOLDER + "reference/Reference.fxml");
		}

		return referenceFXML;
	}
}
