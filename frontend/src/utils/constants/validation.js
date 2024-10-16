// TODO : переработать framesRules
export const framesRules = [
  {
    // TODO: поменять значение
    maxTitleLength: 36,
    maxTitleStrings: 1,
    maxTextLength: 245,
    maxTextStrings: 7,
    // TODO: поменять значение
    maxTitleStringLength: 36,
    maxTextStringLength: 35,
    minLength: 1,
  },
  {
    // TODO: поменять значение
    maxTitleLength: 51,
    maxTitleStrings: 2,
    maxTextLength: 210,
    maxTextStrings: 6,
    maxTitleStringLength: 30,
    maxTextStringLength: 35,
    minLength: 1,
  },
  {
    maxTitleLength: 51,
    maxTitleStrings: 3,
    maxTextLength: 140,
    maxTextStrings: 4,
    maxTitleStringLength: 36,
    maxTextStringLength: 35,
    minLength: 1,
  },
];

// TODO: убрать после переработки framesRules
export const NUMBER_CORRECT_FRAMES_RULE = 2;

export const MAX_IMAGE_SIZE = 500;
export const SUPPORTED_FORMATS = ['image/jpg', 'image/jpeg', 'image/png'];
export const MAX_IMAGE_WIDTH = 1333;
export const MAX_IMAGE_HEIGHT = 2000;
