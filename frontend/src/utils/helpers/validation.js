import * as Yup from 'yup';

import { previewRules } from '../constants/previewValidation';

export const storyValidationSchema = Yup.object({
  stories: Yup.array().of(
    Yup.object().shape({
      previewTitle: Yup.string().required('Поле обязательно'),
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
          pictureUrl: Yup.array().nullable().required('Поле обязательно'),
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
