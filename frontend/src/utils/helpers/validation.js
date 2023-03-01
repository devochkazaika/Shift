import * as Yup from 'yup';

import {
  previewRules,
  MAX_IMAGE_SIZE,
  SUPPORTED_FORMATS,
  MAX_IMAGE_WIDTH,
  MAX_IMAGE_HEIGHT,
} from '../constants/validation';

const checkImageSides = (value) => {
  return new Promise((resolve) => {
    const _URL = window.URL || window.webkitURL;
    const image = new Image();
    const objectUrl = _URL.createObjectURL(value);
    image.src = objectUrl;
    image.onload = () => {
      resolve(image);
      return image.width <= MAX_IMAGE_WIDTH && image.height <= MAX_IMAGE_HEIGHT;
    };
  });
};

export const storyValidationSchema = Yup.object({
  stories: Yup.array().of(
    Yup.object().shape({
      previewTitle: Yup.string().required('Поле обязательно'),
      previewUrl: Yup.mixed()
        .test('fileFormat', 'Неподходящий тип изображения', (value) => {
          if (!value) return true;
          return SUPPORTED_FORMATS.includes(value.type);
        })
        .test('fileSize', `Максимальный допустимый размер - ${MAX_IMAGE_SIZE}КБ`, (value) => {
          if (!value) return true;
          return value.size <= MAX_IMAGE_SIZE * 1024;
        })
        .test(
          'fileSides',
          `Максимальный допустимый размер - ${MAX_IMAGE_WIDTH}x${MAX_IMAGE_HEIGHT}px`,
          (value) => {
            if (!value) return true;
            return checkImageSides(value).then(
              (image) => image.width <= MAX_IMAGE_WIDTH && image.height <= MAX_IMAGE_HEIGHT,
            );
          },
        ),
      storyFrames: Yup.array().of(
        Yup.object().shape({
          title: Yup.string()
            .required('Поле обязательно')
            .max(
              previewRules[2].maxTitleLength,
              `Длина заголовка должна содержать менее ${
                previewRules[2].maxTitleLength + 1
              } символов`,
            ),
          text: Yup.string()
            .required('Поле обязательно')
            .when('title', (titleValue, textSchema) => {
              switch (true) {
                case titleValue &&
                  previewRules[1].maxTitleLength < titleValue.length &&
                  titleValue.length <= previewRules[2].maxTitleLength:
                  return textSchema.max(
                    previewRules[2].maxTextLength,
                    `Длина текста должна быть менее ${previewRules[2].maxTextLength + 1} символа`,
                  );
                case titleValue &&
                  previewRules[0].maxTitleLength < titleValue.length &&
                  titleValue.length <= previewRules[1].maxTitleLength:
                  return textSchema.max(
                    previewRules[1].maxTextLength,
                    `Длина текста должна быть менее ${previewRules[1].maxTextLength + 1} символов`,
                  );
                case titleValue && titleValue.length <= previewRules[0].maxTitleLength:
                  return textSchema.max(
                    previewRules[0].maxTextLength,
                    `Длина текста должна быть менее ${previewRules[0].maxTextLength + 1} символов`,
                  );
                default:
                  return textSchema;
              }
            }),
          pictureUrl: Yup.mixed()
            .nullable()
            .required('Поле обязательно')
            .test(
              'fileFormat',
              'Неподходящий тип изображения',
              (value) => value && SUPPORTED_FORMATS.includes(value.type),
            )
            .test(
              'fileSize',
              `Максимальный допустимый размер - ${MAX_IMAGE_SIZE}КБ`,
              (value) => value && value.size <= MAX_IMAGE_SIZE * 1024,
            )
            .test(
              'fileSides',
              `Максимальный допустимый размер - ${MAX_IMAGE_WIDTH}x${MAX_IMAGE_HEIGHT}px`,
              (value) =>
                value &&
                checkImageSides(value).then(
                  (image) => image.width <= MAX_IMAGE_WIDTH && image.height <= MAX_IMAGE_HEIGHT,
                ),
            ),
          linkText: Yup.string().when('visibleLinkOrButtonOrNone', {
            is: 'LINK',
            then: Yup.string().required('Поле обязательно'),
          }),
          linkUrl: Yup.string().when('visibleLinkOrButtonOrNone', {
            is: 'LINK',
            then: Yup.string().required('Поле обязательно'),
          }),
          buttonText: Yup.string().when('visibleLinkOrButtonOrNone', {
            is: 'BUTTON',
            then: Yup.string().required('Поле обязательно'),
          }),
          buttonUrl: Yup.string().when('visibleLinkOrButtonOrNone', {
            is: 'BUTTON',
            then: Yup.string().required('Поле обязательно'),
          }),
        }),
      ),
    }),
  ),
});
