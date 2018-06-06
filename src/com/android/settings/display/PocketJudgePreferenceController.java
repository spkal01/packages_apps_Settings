/*
 * Copyright (C) 2018 Paranoid Android
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package com.android.settings.display;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v14.preference.SwitchPreference;
import android.support.v7.preference.Preference;

import com.android.settings.DisplaySettings;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.search.DatabaseIndexingUtils;
import com.android.settings.search.InlineSwitchPayload;
import com.android.settings.search.ResultPayload;
import com.android.settings.R;
import com.android.settingslib.core.AbstractPreferenceController;

import static android.provider.Settings.System.POCKET_JUDGE;

public class PocketJudgePreferenceController extends AbstractPreferenceController implements
        PreferenceControllerMixin, Preference.OnPreferenceChangeListener {

    private static final String KEY_POCKET_JUDGE = "pocket_judge";

    public PocketJudgePreferenceController(Context context) {
        super(context);
    }

    @Override
    public String getPreferenceKey() {
        return KEY_POCKET_JUDGE;
    }

    @Override
    public void updateState(Preference preference) {
        int pocketJudgeValue = Settings.System.getInt(mContext.getContentResolver(),
                POCKET_JUDGE, 1);
        ((SwitchPreference) preference).setChecked(pocketJudgeValue != 0);
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        boolean pocketJudgeValue = (Boolean) newValue;
        Settings.System.putInt(mContext.getContentResolver(), POCKET_JUDGE, pocketJudgeValue ? 1 : 0);
        return true;
    }

    @Override
    public ResultPayload getResultPayload() {
        final Intent intent = DatabaseIndexingUtils.buildSubsettingIntent(mContext,
                DisplaySettings.class.getName(), KEY_POCKET_JUDGE,
                mContext.getString(R.string.display_settings));

        return new InlineSwitchPayload(POCKET_JUDGE,
                ResultPayload.SettingsSource.SYSTEM, 1, intent,
                isAvailable(), 1);
    }
}
