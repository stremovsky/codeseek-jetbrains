package codeseek.com;

import com.intellij.ide.browsers.BrowserLauncher;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;

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
        String selectedText = model.getSelectedText().trim();
        if (selectedText.isEmpty()) {
            return;
        }
        // trim to long search string
        if (selectedText.length() > 1024) {
            selectedText = selectedText.substring(0, 1024);
        }
        String url = null;
        try {
            url = "https://codeseek.com/?q=" +  URLEncoder.encode(selectedText, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return;
        }
        BrowserLauncher.getInstance().browse(url, null);
    }
}