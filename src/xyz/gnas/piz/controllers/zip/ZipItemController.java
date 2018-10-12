package xyz.gnas.piz.controllers.zip;

import java.io.File;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import net.lingala.zip4j.progress.ProgressMonitor;
import xyz.gnas.piz.common.CommonConstants;
import xyz.gnas.piz.common.CommonUtility;
import xyz.gnas.piz.common.ResourceManager;

public class ZipItemController {
	@FXML
	private AnchorPane apRoot;

	@FXML
	private Label lblStatus;

	@FXML
	private Label lblOriginal;

	@FXML
	private Label lblZip;

	@FXML
	private HBox hbResult;

	@FXML
	private HBox hbProcess;

	@FXML
	private Button btnPauseResume;

	@FXML
	private ImageView ivPauseResume;

	private ProgressMonitor progress;

	private BooleanProperty isPaused = new SimpleBooleanProperty();

	private int percent;

	@FXML
	private void initialize() {
		try {
			isPaused.addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					try {
						ivPauseResume
								.setImage(newValue ? ResourceManager.getResumeIcon() : ResourceManager.getPauseIcon());
						btnPauseResume.setText(newValue ? CommonConstants.RESUME : CommonConstants.PAUSE);

						if (newValue) {
							lblStatus.setText("Paused (" + percent + "%)");
						}
					} catch (Exception e) {
						CommonUtility.showError(e, "Error handling pause", false);
					}
				}
			});
		} catch (Exception e) {
			CommonUtility.showError(e, "Could not initialise zip item", true);
		}
	}

	public void loadFile(File file) {
		// folder is shown as blue
		if (file.isDirectory()) {
			lblOriginal.setTextFill(Color.BLUE);
		}

		lblOriginal.setText(file.getName());

		// originally set status text ro ready
		lblStatus.setText("Ready");
	}

	public void beginProcess(ProgressMonitor progress, String zipName, BooleanProperty isMasterPaused) {
		this.progress = progress;
		lblZip.setText(zipName);
		hbResult.setVisible(true);
		hbProcess.setVisible(true);
		apRoot.setStyle("-fx-background-color: bisque;");
		btnPauseResume.disableProperty().bind(isMasterPaused);
	}

	public void updateProgress(boolean isObfuscated, boolean isOuter) {
		// only show progress if progress isn't paused and isn't cancelled
		if (!progress.isPause() && !progress.isCancelAllTasks()) {
			percent = progress.getPercentDone();

			// each layer of zip takes roughly 50% of the overall process
			if (isObfuscated) {
				percent /= 2;

				// inner layer is finished
				if (isOuter) {
					percent = 50 + percent;
				}
			}

			showCurrentProgress();
		}
	}

	private void showCurrentProgress() {
		lblStatus.setText("Working (" + percent + "%)");
	}

	public void finishProcess() {
		lblStatus.setText("Finished");
		hbProcess.setVisible(false);
		apRoot.setStyle("-fx-background-color: aliceblue;");
	}

	@FXML
	private void pauseOrResume(ActionEvent event) {
		try {
			isPaused.set(!isPaused.get());
			progress.setPause(isPaused.get());

			// set status to show only percentage done if user resumes
			if (!isPaused.get()) {
				showCurrentProgress();
			}
		} catch (Exception e) {
			CommonUtility.showError(e, "Could not pause the process [" + lblOriginal.getText() + "]", false);
		}
	}

	@FXML
	private void stop(ActionEvent event) {
		try {
			if (CommonUtility.showConfirmation("Are you sure you want to stop this process?")) {
				progress.setPause(false);
				progress.cancelAllTasks();
				lblStatus.setText("Stopped");
				isPaused.set(false);
			}
		} catch (Exception e) {
			CommonUtility.showError(e, "Could not stop the process [" + lblOriginal.getText() + "]", false);
		}
	}
}