package android.telephony;

import android.content.Context;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import com.android.internal.telephony.IUniCarrierConfigLoader;

/* loaded from: classes.dex */
public class UniCarrierConfigManager {
    public static final String ACTION_UNI_CARRIER_CONFIG_CHANGED = "android.telephony.action.UNI_CARRIER_CONFIG_CHANGED";
    public static final String CARRIER_CONFIG_CHANGED_TYPE = "carrier_changed_type";
    public static int CONFIG_FEATURE = 0;
    public static int CONFIG_SUBINFO = 1;
    public static final String KEY_3G_PLUS_BOOL = "H_plus_bool";
    public static final String KEY_ALLOW_CNAP_NAME_IN_CALL_LOG = "allow_cnap_name_in_call_log";
    public static final String KEY_CALL_BARRING_DEFAULT_SERVICE_CLASS_INT = "call_barring_default_service_class_int";
    public static final String KEY_CALL_BARRING_DISABLEALL_SERVICE_CLASS_INT = "call_barring_disableall_service_class_int";
    public static final String KEY_CARRIER_CONFERENCE_PARTICIPANT_LABEL = "show_conference_participant_label";
    public static final String KEY_CARRIER_CONFIG_SMSC_BOOL = "carrier_config_smsc_bool";
    public static final String KEY_CARRIER_CONTACTS_MATCH = "carrier_contacts_match";
    public static final String KEY_CARRIER_DEREG_VOWIFI_BEFORE_ECALL = "deregister_vowifi_before_ecall";
    public static final String KEY_CARRIER_DEREG_VOWIFI_WHEN_CELLULAR_PREFERRED = "deregister_vowifi_when_cellular_preffered";
    public static final String KEY_CARRIER_DIAL_ECALL_VOWIFI_WHEN_AIRPLANE = "dial_ecall_vowifi_when_airplane_mode";
    public static final String KEY_CARRIER_DISABLE_PLAY_HOLD_TONE = "disable_play_hold_tone";
    public static final String KEY_CARRIER_DOWNGRADE_TO_VOICE_WHEN_RTP_TIMEOUT = "carrier_downgrade_to_voice_when_rtp_timeout";
    public static final String KEY_CARRIER_ECALL_ON_VOWIFI_FIRST = "ecall_on_vowifi_first";
    public static final String KEY_CARRIER_IMS_USER_AGENT = "carrier_ims_user_agent";
    public static final String KEY_CARRIER_NAME_OVERRIDE_IN_5G_ROAMING_STATE = "carrier_name_override_in_5g_roaming_state";
    public static final String KEY_CARRIER_RADIO_POWER_ON_FOR_ECALL = "carrier_radio_power_on_for_ecall";
    public static final String KEY_CARRIER_RETRY_ECALL_CELLULAR_NETWORK = "retry_ecall_cellualr_network";
    public static final String KEY_CARRIER_RETRY_ECALL_VOWIFI = "retry_ecall_vowifi";
    public static final String KEY_CARRIER_SHOW_ALTERNATIVE_SEVICE_ERROR_DIALOG = "show_alternative_service_error_dialog";
    public static final String KEY_CARRIER_SUPPORTS_VOWIFI_MMS = "support_vowifi_mms";
    public static final String KEY_CARRIER_SUPPORT_CHANGE_VIDEO_TYPE = "support_change_video_type";
    public static final String KEY_CARRIER_SUPPORT_DISABLE_UT_BY_NETWORK = "support_disable_ut_by_network";
    public static final String KEY_CARRIER_SUPPORT_MULTI_VIDEO_CALL = "support_multi_video_call";
    public static final String KEY_CARRIER_SUPPORT_VIDEO_CALL_TX_RX_CONTROL = "support_video_call_tx_rx_control";
    public static final String KEY_CARRIER_SUPPORT_VOWIFI_ECALL = "support_vowifi_ecall";
    public static final String KEY_CARRIER_WFC_SUPPORTS_IMS_PREFERRED_BOOL = "carrier_wfc_supports_ims_preferred_bool";
    public static final String KEY_CFNR_NOREPLY_TIME_INT = "cfnr_noreply_time_int";
    public static final String KEY_CHECK_ALL_CF_AFTER_UPDATE_CF = "key_check_all_cf_after_update_cf";
    public static final String KEY_DEFAULT_SHOW_WIFI_CALL = "default_show_wifi_call";
    public static final String KEY_DEFAULT_SMSC_NUMBER_STRING = "default_smsc_number_string";
    public static final String KEY_DETACH_DELAY_TIME = "detach_delay_time";
    public static final String KEY_DISPLAY_CALL_FORWARD_ALL_SERVICE_CLASS_BOOL = "display_call_forward_all_service_class";
    public static final String KEY_DISPLAY_NAME_SHOW_BOOL = "display_name_show_bool";
    public static final String KEY_EDITABLE_VT_RESOLUTION_BOOL = "editable_vt_resolution_bool";
    public static final String KEY_EXIT_ON_NETWORK_SELECT_SUCCEED = "exit_on_network_select_succeed";
    public static final String KEY_FEATURE_AUTOMATIC_CALL_RECORD_ENABLED_BOOL = "automatic_call_record_enabled_bool";
    public static final String KEY_FEATURE_FADE_IN_ENABLED_BOOL = "fade_in_enabled_bool";
    public static final String KEY_FEATURE_FLIP_SILENT_INCOMING_CALL_ENABLED_BOOL = "flip_to_silent_incoming_call_enabled_bool";
    public static final String KEY_FEATURE_HD_AUDIO = "support_att_hd_audio_feature";
    public static final String KEY_FEATURE_PLAY_CALL_HOLD_TONE_BOOL = "carrier_feature_play_call_hold_tone_bool";
    public static final String KEY_FEATURE_PLAY_RBT_BOOL_TYPE_INT = "carrier_feature_play_rbt_type_int";
    public static final String KEY_FEATURE_VIBRATE_FOR_CALL_CONNECTION_BOOL = "vibrate_for_call_connection_bool";
    public static final String KEY_FORCE_CELLULAR_NETWORK_AVAILABLE_FOR_ECALL = "force_cellular_network_available_for_ecall";
    public static final String KEY_HD_VOICE_ICON_SHOULD_BE_REMOVED = "HD_voice_icon_should_be_removed";
    public static final String KEY_HIDE_APN_TYPES_STRING_ARRAY = "hide_apn_types_string_array";
    public static final String KEY_HIDE_ENHANCED_4G_LTE_BY_NETWORK = "hide_enhanced_4g_lte_by_network";
    public static final String KEY_HIDE_VT_RESOLUTION_BOOL = "hide_vt_resolution_bool";
    public static final String KEY_HPLUS_DATA_DISTINGUISHABLE = "hplus_data_distinguishable";
    public static final String KEY_HSPAP_SHOW_4G = "hspap_show_4g";
    public static final String KEY_HSPA_DATA_DISTINGUISHABLE = "hspa_data_distinguishable";
    public static final String KEY_IGNORE_NETWORK_SCAN_UNDER_2G = "ignore_network_scan_under_2g";
    public static final String KEY_IGNORE_NETWORK_SCAN_UNDER_3G = "ignore_network_scan_under_3g";
    public static final String KEY_IMS_LOG_ANSWER_ELSEWHERE_CALL = "ims_log_answer_elsewhere_call";
    public static final String KEY_KEEP_XCAP_TIME_INT = "key_keep_xcap_time_int";
    public static final String KEY_MANAGE_CONFERENCE_EVEN_CSFB = "manage_conference_even_csfb_bool";
    public static final String KEY_MT_REQUEST_MEDIA_CHANGE_TIMER = "mt_request_media_change_timer";
    public static final String KEY_OEM_PERMANENT_AUTO_SEL_MODE = "oem_key_permanent_auto_sel_mode_bool";
    public static final String KEY_OEM_RESTORE_AUTO_MODE = "oem_key_restore_auto_mode";
    public static final String KEY_OPERATOR_STRING_SHOW_WIFI_CALL = "operator_string_show_wifi_call";
    public static final String KEY_PRESET_WIFI_EAP_METHOD = "wifi.preset_wifi_eap_method";
    public static final String KEY_PRESET_WIFI_NETWORK_CONFIG = "wifi.preset_wifi_network_config";
    public static final String KEY_PRESET_WIFI_NETWORK_SUGGESTION = "wifi.preset_wifi_network_suggestion";
    public static final String KEY_PRESET_WIFI_PASSPOINT_NETWORK = "wifi.preset_wifi_passpoint_network";
    public static final String KEY_REDIR_ECC_PFE_FOR_WIFI_CALL_380 = "redir_ecc_pre_for_wifi_call_380";
    public static final String KEY_REMOTE_DISCONNECT_UNHOLD_BACKGROUND_CALL_BOOL = "support_unhold_background_call";
    public static final String KEY_ROAMING_PLMN_OVERRIDE_BOOL = "roaming_plmn_override_bool";
    public static final String KEY_SHOW_3G_FOR_SIM = "show_3g_for_sim";
    public static final String KEY_SHOW_3G_PLUS = "show_3g_plus";
    public static final String KEY_SHOW_CALLTIMER_WHEN_CALL_ONHOLD = "show_calltimer_when_call_onhold";
    public static final String KEY_SHOW_CALL_BARRING_PASSWORD_BOOL = "show_call_barring_password_bool";
    public static final String KEY_SHOW_CALL_ELAPSED_TIME = "show_call_elapsed_time";
    public static final String KEY_SHOW_CONFERENCE_MAX_SIZE_LIMIT_TOAST = "show_conference_max_size_limit_toast";
    public static final String KEY_SHOW_IMS_CAPABILITY_CHANGE_TOAST = "show_ims_capability_change_toast";
    public static final String KEY_SHOW_INCOMING_INTERNATIONAL_ROAMING_BARRING = "show_incoming_international_raoming_barring";
    public static final String KEY_SHOW_LOCATION_ON_CALLER_UI = "show_location_on_caller_ui_when_ecc";
    public static final String KEY_SHOW_NETWORK_SELECTION_FAILED = "show_network_selection_fail";
    public static final String KEY_SHOW_NO_SERVICE_VOLTE_ICON = "show_no_service_volte_icon";
    public static final String KEY_SHOW_NUMBER_AND_NAME = "show_number_and_name";
    public static final String KEY_SHOW_SPECIFIC_WIFI_CALLING = "show_specific_wifi_calling";
    public static final String KEY_SHOW_SPECIFIC_WIFI_CALLING_STRING = "show_specific_wifi_calling_string";
    public static final String KEY_SHOW_VOLTE_ICON = "show_volte_icon";
    public static final String KEY_SHOW_VOLTE_ICON_TYPE_INT = "show_volte_icon_int";
    public static final String KEY_SHOW_VOWIFI_ICON_TYPE_INT = "show_vowifi_icon_int";
    public static final String KEY_SKIP_CF_FAIL_TO_REGISTER_DIALOG_BOOL = "skip_cf_fail_to_register_dialog_bool";
    public static final String KEY_STA_CARRIER_PASSPOINT_NETWORK = "wifi.station_carrier_passpoint_network";
    public static final String KEY_STK_DIFFERENT_LAUNCH_BROWSER_TR = "stk_different_launch_browser_tr";
    public static final String KEY_SUPL_CER_STRING = "gps.supl_cer";
    public static final String KEY_SUPL_CER_VERIFY_BOOL = "gps.supl_cer_verify";
    public static final String KEY_SUPPORTS_VIDEO_CALLFORWARD_BOOL = "supports_video_callforward_bool";
    public static final String KEY_SUPPORT_CS_REDIAL_BOOL = "carrier_support_cs_redial";
    public static final String KEY_SUPPORT_CUSTOMIZED_VIDEO_ALERTING_TONE = "support_customized_video_alerting_tone";
    public static final String KEY_SUPPORT_CUSTOMIZED_VIDEO_ANNOUNCEMENTS = "support_customized_video_announcements";
    public static final String KEY_SUPPORT_CUSTOMIZED_VIDEO_RINGING_SIGNAL = "support_customized_video_ringing_signal";
    public static final String KEY_SUPPORT_ECT_BOOL = "support_ect_bool";
    public static final String KEY_SUPPORT_MODIFY_DIALSTRING_FOR_ECALL = "support_modify_dialstring_for_ecall";
    public static final String KEY_SUPPORT_SCREEN_INTERACTION = "support_screen_interaction";
    public static final String KEY_SUPPORT_SET_SS_FLAG = "key_support_set_ss_flag";
    public static final String KEY_SUPPORT_SHOW_WIFI_CALLING_PREFERENCE = "support_show_wifi_calling_preference";
    public static final String KEY_SUPPORT_UP_DOWN_GRADE_VT_CONFERENCE = "support_up_down_vt_conference";
    public static final String KEY_SUPPORT_VIDEO_CUSTOMER_SERVICE = "support_video_customer_service";
    public static final String KEY_SUPPORT_VOICE_CLEAR_CODE = "support_voice_clear_code";
    public static final String KEY_SUPPORT_VOICE_CLEAR_CODE_SPECIAL = "support_voice_clear_code_special";
    public static final String KEY_SUPPORT_VOICE_CLEAR_CODE_VOLTE_CSFB = "support_voice_clear_code_volte_csfb";
    public static final String KEY_SYNCHRONOUS_SETTING_FOR_WFC_VOLTE = "synchronous_setting_for_wfc_volte";
    public static final String KEY_VIDEO_CALLING_ON_BY_DEFAULT_BOOL = "video_calling_on_by_default_bool";
    public static final String KEY_XCAP_DELAY_TIME_INT = "key_xcap_delay_time_int";
    private static final String TAG = "UniCarrierConfigManager";
    private static final PersistableBundle sDefaults;
    private final Context mContext;

    static {
        PersistableBundle persistableBundle = new PersistableBundle();
        sDefaults = persistableBundle;
        persistableBundle.putBoolean(KEY_CARRIER_SUPPORT_MULTI_VIDEO_CALL, true);
        persistableBundle.putBoolean(KEY_HSPAP_SHOW_4G, false);
        persistableBundle.putBoolean(KEY_HSPA_DATA_DISTINGUISHABLE, false);
        persistableBundle.putBoolean(KEY_SHOW_3G_PLUS, false);
        persistableBundle.putBoolean(KEY_SHOW_3G_FOR_SIM, false);
        persistableBundle.putBoolean(KEY_SHOW_VOLTE_ICON, true);
        persistableBundle.putInt(KEY_SHOW_VOLTE_ICON_TYPE_INT, 0);
        persistableBundle.putBoolean(KEY_SHOW_NO_SERVICE_VOLTE_ICON, false);
        persistableBundle.putInt(KEY_SHOW_VOWIFI_ICON_TYPE_INT, 0);
        persistableBundle.putBoolean(KEY_SYNCHRONOUS_SETTING_FOR_WFC_VOLTE, true);
        persistableBundle.putBoolean(KEY_SHOW_IMS_CAPABILITY_CHANGE_TOAST, true);
        persistableBundle.putLong(KEY_MT_REQUEST_MEDIA_CHANGE_TIMER, 10000L);
        persistableBundle.putBoolean(KEY_CARRIER_DOWNGRADE_TO_VOICE_WHEN_RTP_TIMEOUT, false);
        persistableBundle.putBoolean(KEY_CARRIER_SUPPORT_VIDEO_CALL_TX_RX_CONTROL, true);
        persistableBundle.putBoolean(KEY_CARRIER_SUPPORT_CHANGE_VIDEO_TYPE, false);
        persistableBundle.putBoolean(KEY_MANAGE_CONFERENCE_EVEN_CSFB, false);
        persistableBundle.putBoolean(KEY_CARRIER_SUPPORT_DISABLE_UT_BY_NETWORK, false);
        persistableBundle.putBoolean(KEY_CARRIER_SUPPORT_VOWIFI_ECALL, true);
        persistableBundle.putBoolean(KEY_VIDEO_CALLING_ON_BY_DEFAULT_BOOL, true);
        persistableBundle.putBoolean(KEY_CARRIER_ECALL_ON_VOWIFI_FIRST, false);
        persistableBundle.putBoolean(KEY_CARRIER_DIAL_ECALL_VOWIFI_WHEN_AIRPLANE, false);
        persistableBundle.putInt(KEY_CALL_BARRING_DISABLEALL_SERVICE_CLASS_INT, 0);
        persistableBundle.putBoolean(KEY_CARRIER_RETRY_ECALL_VOWIFI, false);
        persistableBundle.putBoolean(KEY_CARRIER_RETRY_ECALL_CELLULAR_NETWORK, false);
        persistableBundle.putBoolean(KEY_CARRIER_DEREG_VOWIFI_BEFORE_ECALL, false);
        persistableBundle.putBoolean(KEY_CARRIER_DEREG_VOWIFI_WHEN_CELLULAR_PREFERRED, false);
        persistableBundle.putIntArray(KEY_CARRIER_CONTACTS_MATCH, new int[0]);
        persistableBundle.putBoolean(KEY_SUPPORT_UP_DOWN_GRADE_VT_CONFERENCE, false);
        persistableBundle.putBoolean(KEY_FEATURE_AUTOMATIC_CALL_RECORD_ENABLED_BOOL, true);
        persistableBundle.putBoolean(KEY_CARRIER_SUPPORTS_VOWIFI_MMS, false);
        persistableBundle.putBoolean(KEY_SUPPORT_CUSTOMIZED_VIDEO_ALERTING_TONE, false);
        persistableBundle.putBoolean(KEY_SUPPORT_CUSTOMIZED_VIDEO_ANNOUNCEMENTS, false);
        persistableBundle.putBoolean(KEY_SUPPORT_SCREEN_INTERACTION, false);
        persistableBundle.putBoolean(KEY_CARRIER_CONFERENCE_PARTICIPANT_LABEL, true);
        persistableBundle.putBoolean(KEY_FEATURE_VIBRATE_FOR_CALL_CONNECTION_BOOL, true);
        persistableBundle.putBoolean(KEY_FEATURE_FLIP_SILENT_INCOMING_CALL_ENABLED_BOOL, true);
        persistableBundle.putBoolean(KEY_FEATURE_FADE_IN_ENABLED_BOOL, true);
        persistableBundle.putStringArray(KEY_HIDE_APN_TYPES_STRING_ARRAY, new String[]{"xcap"});
        persistableBundle.putBoolean(KEY_SHOW_NUMBER_AND_NAME, false);
        persistableBundle.putBoolean(KEY_HD_VOICE_ICON_SHOULD_BE_REMOVED, false);
        persistableBundle.putBoolean(KEY_FEATURE_HD_AUDIO, false);
        persistableBundle.putBoolean(KEY_REMOTE_DISCONNECT_UNHOLD_BACKGROUND_CALL_BOOL, true);
        persistableBundle.putBoolean(KEY_IMS_LOG_ANSWER_ELSEWHERE_CALL, true);
        persistableBundle.putBoolean(KEY_IGNORE_NETWORK_SCAN_UNDER_2G, false);
        persistableBundle.putBoolean(KEY_IGNORE_NETWORK_SCAN_UNDER_3G, false);
        persistableBundle.putBoolean(KEY_HIDE_ENHANCED_4G_LTE_BY_NETWORK, false);
        persistableBundle.putBoolean(KEY_SUPPORT_SHOW_WIFI_CALLING_PREFERENCE, true);
        persistableBundle.putBoolean(KEY_DEFAULT_SHOW_WIFI_CALL, true);
        persistableBundle.putString(KEY_OPERATOR_STRING_SHOW_WIFI_CALL, "");
        persistableBundle.putBoolean(KEY_CHECK_ALL_CF_AFTER_UPDATE_CF, false);
        persistableBundle.putBoolean(KEY_EDITABLE_VT_RESOLUTION_BOOL, true);
        persistableBundle.putBoolean(KEY_SHOW_INCOMING_INTERNATIONAL_ROAMING_BARRING, true);
        persistableBundle.putBoolean(KEY_SUPPORT_VOICE_CLEAR_CODE, false);
        persistableBundle.putBoolean(KEY_SUPPORT_VOICE_CLEAR_CODE_SPECIAL, false);
        persistableBundle.putBoolean(KEY_SUPPORT_VOICE_CLEAR_CODE_VOLTE_CSFB, false);
        persistableBundle.putBoolean(KEY_STK_DIFFERENT_LAUNCH_BROWSER_TR, false);
        persistableBundle.putBoolean(KEY_SUPPORTS_VIDEO_CALLFORWARD_BOOL, false);
        persistableBundle.putInt(KEY_KEEP_XCAP_TIME_INT, 5000);
        persistableBundle.putInt(KEY_XCAP_DELAY_TIME_INT, 125000);
        persistableBundle.putBoolean(KEY_SUPPORT_SET_SS_FLAG, false);
        persistableBundle.putBoolean(KEY_CARRIER_RADIO_POWER_ON_FOR_ECALL, true);
        persistableBundle.putInt(KEY_FEATURE_PLAY_RBT_BOOL_TYPE_INT, 0);
        persistableBundle.putBoolean(KEY_FEATURE_PLAY_CALL_HOLD_TONE_BOOL, false);
        persistableBundle.putBoolean(KEY_CARRIER_CONFIG_SMSC_BOOL, false);
        persistableBundle.putString(KEY_DEFAULT_SMSC_NUMBER_STRING, "");
        persistableBundle.putBoolean(KEY_SHOW_NETWORK_SELECTION_FAILED, true);
        persistableBundle.putBoolean(KEY_OEM_RESTORE_AUTO_MODE, false);
        persistableBundle.putBoolean(KEY_EXIT_ON_NETWORK_SELECT_SUCCEED, false);
        persistableBundle.putBoolean(KEY_OEM_PERMANENT_AUTO_SEL_MODE, true);
        persistableBundle.putBoolean(KEY_FORCE_CELLULAR_NETWORK_AVAILABLE_FOR_ECALL, false);
        persistableBundle.putString(KEY_CARRIER_IMS_USER_AGENT, "");
        persistableBundle.putBoolean(KEY_3G_PLUS_BOOL, false);
        persistableBundle.putInt(KEY_DETACH_DELAY_TIME, 0);
        persistableBundle.putBoolean(KEY_SUPPORT_VIDEO_CUSTOMER_SERVICE, false);
        persistableBundle.putBoolean(KEY_CARRIER_WFC_SUPPORTS_IMS_PREFERRED_BOOL, false);
        persistableBundle.putBoolean(KEY_HIDE_VT_RESOLUTION_BOOL, false);
        persistableBundle.putStringArray(KEY_PRESET_WIFI_NETWORK_CONFIG, null);
        persistableBundle.putStringArray(KEY_PRESET_WIFI_NETWORK_SUGGESTION, null);
        persistableBundle.putStringArray(KEY_PRESET_WIFI_PASSPOINT_NETWORK, null);
        persistableBundle.putStringArray(KEY_PRESET_WIFI_EAP_METHOD, null);
        persistableBundle.putBoolean(KEY_SUPPORT_CUSTOMIZED_VIDEO_RINGING_SIGNAL, false);
        persistableBundle.putBoolean(KEY_SUPPORT_CS_REDIAL_BOOL, true);
        persistableBundle.putBoolean(KEY_SKIP_CF_FAIL_TO_REGISTER_DIALOG_BOOL, false);
        persistableBundle.putBoolean(KEY_SUPPORT_MODIFY_DIALSTRING_FOR_ECALL, true);
        persistableBundle.putBoolean(KEY_SHOW_SPECIFIC_WIFI_CALLING, false);
        persistableBundle.putString(KEY_SHOW_SPECIFIC_WIFI_CALLING_STRING, "");
        persistableBundle.putBoolean(KEY_ALLOW_CNAP_NAME_IN_CALL_LOG, false);
        persistableBundle.putStringArray(KEY_STA_CARRIER_PASSPOINT_NETWORK, null);
        persistableBundle.putString(KEY_SUPL_CER_STRING, "/data/vendor/gnss/supl/spirentroot.cer");
        persistableBundle.putBoolean(KEY_SUPL_CER_VERIFY_BOOL, false);
        persistableBundle.putBoolean(KEY_SHOW_CONFERENCE_MAX_SIZE_LIMIT_TOAST, false);
        persistableBundle.putBoolean(KEY_CARRIER_NAME_OVERRIDE_IN_5G_ROAMING_STATE, false);
        persistableBundle.putBoolean(KEY_SUPPORT_ECT_BOOL, false);
        persistableBundle.putBoolean(KEY_SHOW_CALL_ELAPSED_TIME, false);
        persistableBundle.putBoolean(KEY_SHOW_CALLTIMER_WHEN_CALL_ONHOLD, false);
        persistableBundle.putBoolean(KEY_CARRIER_DISABLE_PLAY_HOLD_TONE, false);
        persistableBundle.putBoolean(KEY_DISPLAY_CALL_FORWARD_ALL_SERVICE_CLASS_BOOL, true);
        persistableBundle.putBoolean(KEY_CARRIER_SHOW_ALTERNATIVE_SEVICE_ERROR_DIALOG, false);
        persistableBundle.putInt(KEY_CFNR_NOREPLY_TIME_INT, 20);
        persistableBundle.putBoolean(KEY_DISPLAY_NAME_SHOW_BOOL, false);
        persistableBundle.putBoolean(KEY_SHOW_LOCATION_ON_CALLER_UI, false);
        persistableBundle.putBoolean(KEY_ROAMING_PLMN_OVERRIDE_BOOL, false);
        persistableBundle.putBoolean(KEY_REDIR_ECC_PFE_FOR_WIFI_CALL_380, true);
        persistableBundle.putInt(KEY_CALL_BARRING_DEFAULT_SERVICE_CLASS_INT, 0);
        persistableBundle.putBoolean(KEY_SHOW_CALL_BARRING_PASSWORD_BOOL, true);
    }

    public UniCarrierConfigManager(Context context) {
        this.mContext = context;
    }

    public static PersistableBundle getDefaultConfig() {
        return new PersistableBundle(sDefaults);
    }

    public PersistableBundle getConfigForSubId(int subId) {
        try {
            IUniCarrierConfigLoader loader = getIUniCarrierConfigLoader();
            if (loader == null) {
                Rlog.w(TAG, "Error getting config for subId " + subId + " IUniCarrierConfigLoader is null");
                return null;
            }
            return loader.getConfigForSubIdWithFeature(subId, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
        } catch (RemoteException ex) {
            Rlog.e(TAG, "Error getting config for subId " + subId + ": " + ex.toString());
            return null;
        }
    }

    public PersistableBundle getConfigForDefaultPhone() {
        int defaultSubId = SubscriptionManager.getDefaultSubscriptionId();
        if (!SubscriptionManager.isValidPhoneId(SubscriptionManager.getPhoneId(defaultSubId))) {
            defaultSubId = Integer.MAX_VALUE;
        }
        try {
            IUniCarrierConfigLoader loader = getIUniCarrierConfigLoader();
            if (loader != null) {
                return loader.getConfigForSubIdWithFeature(defaultSubId, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
            }
            return null;
        } catch (RemoteException ex) {
            Rlog.e(TAG, "Error getting config for default phone " + Integer.toString(defaultSubId) + ": " + ex.toString());
            return null;
        } catch (NullPointerException ex2) {
            Rlog.e(TAG, "Error getting config for default phone " + Integer.toString(defaultSubId) + ": " + ex2.toString());
            return null;
        }
    }

    public void overrideConfig(int subscriptionId, PersistableBundle overrideValues, boolean persistent) {
        try {
            IUniCarrierConfigLoader loader = getIUniCarrierConfigLoader();
            if (loader == null) {
                Rlog.w(TAG, "Error setting config for subId " + subscriptionId + " IUniCarrierConfigLoader is null");
            } else {
                loader.overrideConfig(subscriptionId, overrideValues, persistent);
            }
        } catch (RemoteException ex) {
            Rlog.e(TAG, "Error setting config for subId " + subscriptionId + ": " + ex.toString());
        }
    }

    public PersistableBundle getConfigByComponentForSubId(String prefix, int subId) {
        PersistableBundle configs = getConfigForSubId(subId);
        if (configs == null) {
            return null;
        }
        PersistableBundle ret = new PersistableBundle();
        for (String configKey : configs.keySet()) {
            if (configKey.startsWith(prefix)) {
                addConfig(configKey, configs.get(configKey), ret);
            }
        }
        return ret;
    }

    private void addConfig(String key, Object value, PersistableBundle configs) {
        if (value instanceof String) {
            configs.putString(key, (String) value);
        }
        if (value instanceof String[]) {
            configs.putStringArray(key, (String[]) value);
        }
        if (value instanceof Integer) {
            configs.putInt(key, ((Integer) value).intValue());
        }
        if (value instanceof Long) {
            configs.putLong(key, ((Long) value).longValue());
        }
        if (value instanceof Double) {
            configs.putDouble(key, ((Double) value).doubleValue());
        }
        if (value instanceof Boolean) {
            configs.putBoolean(key, ((Boolean) value).booleanValue());
        }
        if (value instanceof int[]) {
            configs.putIntArray(key, (int[]) value);
        }
        if (value instanceof double[]) {
            configs.putDoubleArray(key, (double[]) value);
        }
        if (value instanceof boolean[]) {
            configs.putBooleanArray(key, (boolean[]) value);
        }
        if (value instanceof long[]) {
            configs.putLongArray(key, (long[]) value);
        }
    }

    public PersistableBundle getConfig() {
        return getConfigForSubId(SubscriptionManager.getDefaultSubscriptionId());
    }

    private IUniCarrierConfigLoader getIUniCarrierConfigLoader() {
        return IUniCarrierConfigLoader.Stub.asInterface(ServiceManager.getService("uni_carrierconfig"));
    }
}