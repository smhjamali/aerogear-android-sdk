package org.aerogear.mobile.security.checks;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import android.app.admin.DevicePolicyManager;
import android.content.Context;

import org.aerogear.mobile.security.SecurityCheckResult;

@RunWith(RobolectricTestRunner.class)
public class EncryptionCheckTest {

    @Mock
    Context context;

    @Mock
    DevicePolicyManager devicePolicyManager;

    EncryptionCheck check;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        check = new EncryptionCheck();
        when(context.getSystemService(context.DEVICE_POLICY_SERVICE))
                        .thenReturn(devicePolicyManager);
    }

    @Test
    public void hasEncryptionEnabled() {
        when(devicePolicyManager.getStorageEncryptionStatus())
                        .thenReturn(DevicePolicyManager.ENCRYPTION_STATUS_ACTIVE);
        SecurityCheckResult result = check.test(context);
        assertTrue(result.passed());
    }

    @Test
    public void hasEncryptionDisabled() {
        when(devicePolicyManager.getStorageEncryptionStatus())
                        .thenReturn(DevicePolicyManager.ENCRYPTION_STATUS_INACTIVE);
        SecurityCheckResult result = check.test(context);
        assertFalse(result.passed());
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullContextTest() {
        check.test(null);
    }

}
