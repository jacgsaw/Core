package coe.cyberbank.arxan;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GuardReaction extends AppCompatActivity {
    private static Activity s_instance = null;
    private static final String RES_NAME = "arxan_dialog_activity";
    private static final String RES_TYPE = "layout";

    public GuardReaction() {
    }

    public static void setInstance(Activity inst) {
        s_instance = inst;
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle((CharSequence)null);
        setContentView(getResources().getIdentifier(RES_NAME, RES_TYPE, getPackageName()));
        if (savedInstanceState == null) {
        }
    }

    public static void resourceVerification() {
        ArxanDialog customDialog = new ArxanDialog(s_instance, "#503-115");
        customDialog.show();
    }

    public static void signatureCheck() {
        ArxanDialog customDialog = new ArxanDialog(s_instance, "#503-116");
        customDialog.show();
    }

    public static void checksumGuard() {
        ArxanDialog customDialog = new ArxanDialog(s_instance, "#503-117");
        customDialog.show();
    }

    public static void debuggerGuard() {
        ArxanDialog customDialog = new ArxanDialog(s_instance, "#503-118");
        customDialog.show();
    }

    public static void dynamicInstrumentation() {
        ArxanDialog customDialog = new ArxanDialog(s_instance, "#503-119");
        customDialog.show();
    }

    public static void hookDetection() {
        ArxanDialog customDialog = new ArxanDialog(s_instance, "#503-121");
        customDialog.show();
    }

    public static void rootDetection() {
        ArxanDialog customDialog = new ArxanDialog(s_instance, "#503-122");
        customDialog.show();
    }

    public static void maliciousPackage() {
        ArxanDialog customDialog = new ArxanDialog(s_instance, "#503-123");
        customDialog.show();
    }

    public static void virtualizationDectection() {
        ArxanDialog customDialog = new ArxanDialog(s_instance, "#503-125");
        customDialog.show();
    }

    public static void virtualControl() {
        ArxanDialog customDialog = new ArxanDialog(s_instance, "#503-125");
        customDialog.show();
    }

    public static void emulatorDetection() {
        ArxanDialog customDialog = new ArxanDialog(s_instance, "#503-120");
        customDialog.show();
    }
}

