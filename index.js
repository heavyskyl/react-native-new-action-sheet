/**
 * @flow
 */

/* eslint-disable import/no-unresolved */
import {
    NativeModules,
    Platform,
    ActionSheetIOS,
} from 'react-native';
/* eslint-anable import/no-unresolved */

const ActionSheet = Platform.OS === 'ios' ? ActionSheetIOS : NativeModules.RNBottomSheet;

export type ShareParams = {
    url?: string,
    message?: string,
    subject?: string,
    excludedActivityTypes?: Array<string>,
    title?: String, // required for android
}

export default {
    showActionSheet(buttons: Array<string>, callback: Function, extraOptions: Object = {}) {
        ActionSheet.showActionSheetWithOptions(
            {
                options: buttons,
                cancelButtonIndex: buttons.length - 1,
                ...extraOptions,
            },
            callback,
        );
    },

    /**
     *
     * @param {Object} params
     * @param {Function} errorCb
     * @param {Function} successCb - will work only on ios
     */
    showShareSheet(
        params: ShareParams,
        errorCb: Function = () => undefined,
        successCb: Function = () => undefined, // only for IOS
    ) {
        ActionSheet.showShareActionSheetWithOptions(
            params,
            errorCb,
            successCb,
        );
    },
};
