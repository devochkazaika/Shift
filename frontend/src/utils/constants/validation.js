// TODO : переработать framesRules
export const framesRules = [
  {
    maxTitleLength: 17,
    maxTitleStrings: 1,
    maxTextLength: 245,
    maxTextStrings: 7,
    maxTitleStringLength: 17,
    maxTextStringLength: 35,
    minLength: 1,
  },
  {
    maxTitleLength: 34,
    maxTitleStrings: 2,
    maxTextLength: 210,
    maxTextStrings: 6,
    maxTitleStringLength: 17,
    maxTextStringLength: 35,
    minLength: 1,
  },
  {
    maxTitleLength: 51,
    maxTitleStrings: 3,
    maxTextLength: 140,
    maxTextStrings: 4,
    maxTitleStringLength: 17,
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
