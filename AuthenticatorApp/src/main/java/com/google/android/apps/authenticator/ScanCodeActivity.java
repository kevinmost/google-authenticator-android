package com.google.android.apps.authenticator;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.apps.authenticator.testability.TestableActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

import java.util.Arrays;

public class ScanCodeActivity extends TestableActivity implements ZXingScannerView.ResultHandler {

  private ZXingScannerView mScannerView;

  @Override
  public void onCreate(Bundle state) {
    super.onCreate(state);
    mScannerView = new ZXingScannerView(this) {{
      setFormats(Arrays.asList(BarcodeFormat.QR_CODE));
      setAutoFocus(true);
    }};
    setContentView(mScannerView);
  }

  @Override
  public void onResume() {
    super.onResume();
    mScannerView.setResultHandler(this);
    mScannerView.startCamera();
  }

  @Override
  public void onPause() {
    super.onPause();
    mScannerView.stopCamera();
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    setResult(AuthenticatorActivity.RESULT_CANCELED);
    mScannerView.stopCamera();
    finish();
  }

  @Override
  public void handleResult(Result result) {
    final String scanResult = result.getText();
    setResult(AuthenticatorActivity.RESULT_OK, new Intent().putExtra("SCAN_RESULT", scanResult));
    mScannerView.stopCamera();
    finish();
  }
}
