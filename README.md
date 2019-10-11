# react-native-new-action-sheet

React Native Action Sheet module for android and ios

### Instalation

```bash
yarn add react-native-new-action-sheet
```

You need to link it manually on android if you are using react-native < 60.


### How to use ?

```javascript
import ActionSheet from 'react-native-new-action-sheet';

ActionSheet.showActionSheet(['Banana', 'Apple', 'Cancel'], (index) => {
    // check that not cancel button was pressed in the action-sheet
    if (index !== 2) {
        console.log(`index:${index} was pressed`);
    }
});

const params = Platform.OS === 'ios' ? {
    subject: '',
    message: 'Message to share',
} : {
    url: '',
    subject: '',
    message: 'Message to share',
    title: 'Share',
};

ActionSheet.showShareSheet(params);
```

### Description

On IOS it's a wrapper on ActionSheetIOS.
On Android it's a custom solution.
