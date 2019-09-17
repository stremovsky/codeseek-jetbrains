package codeseek.com;

import com.intellij.ide.browsers.BrowserLauncher;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.application.ApplicationInfo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class CodeseekPlugin extends AnAction {

    public CodeseekPlugin() {
        super("Codeseek this");
    }

    @Override
    public void update(AnActionEvent e) {
        //e.getPresentation().setIcon(AllIcons.Ide.Info_notifications);
        super.update(e);
        Presentation presentation = e.getPresentation();
        presentation.setEnabled(true); // disable the toolbar icon or
    }

    @Override
    public void actionPerformed(AnActionEvent event) {
        Editor editor = event.getData(PlatformDataKeys.EDITOR);
        if (editor == null) {
            return;
        }
        SelectionModel model = editor.getSelectionModel();
        if (model == null) {
            return;
        }
        String selectedText = model.getSelectedText();
        if (selectedText == null) {
            return;
        }
        selectedText = selectedText.trim();
        if (selectedText.isEmpty()) {
            return;
        }
        // trim to long search string
        if (selectedText.length() > 1024) {
            selectedText = selectedText.substring(0, 1024);
        }
        //ApplicationManager.getApplication().ge
        String url = null;
        try {
            ApplicationInfo appInfo = ApplicationInfo.getInstance();
            String appName = URLEncoder.encode(appInfo.getFullApplicationName());
            url = "https://codeseek.com/?app=" + appName +
                    "&q=" +  URLEncoder.encode(selectedText, "UTF-8");
            BrowserLauncher.getInstance().browse(url, null);
        } catch (UnsupportedEncodingException e) {
            return;
        } catch (Exception e) {
            return;
        }
    }
}