import * as Yup from "yup";

import {
  framesRules,
  NUMBER_CORRECT_FRAMES_RULE,
  MAX_IMAGE_HEIGHT,
  MAX_IMAGE_SIZE,
  MAX_IMAGE_WIDTH,
  SUPPORTED_FORMATS,
} from "../constants/validation";

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

const getStrings = (text) => {
  return text.split("\n");
};

const checkStringsCount = (value, maxStringsCount) => {
  return getStrings(value).length <= maxStringsCount;
};

const checkLength = (value, maxLength) => {
  const strings = getStrings(value);
  const valueSymbolsCount = strings.reduce(
    (count, str) => (count += str.length),
    0,
  );
  return valueSymbolsCount <= maxLength;
};

const checkTitleStrings = (value) => {
  if (!value) return true;
  return checkStringsCount(value, framesRules.length);
};

const checkTitleLength = (value, { createError }) => {
  if (!value || !checkTitleStrings(value)) return true;

  const titleStringsCount = getStrings(value).length;
  const rule = framesRules[titleStringsCount - 1];

  if (!checkLength(value, rule.maxTitleLength))
    return createError({
      message: `Максимальное кол-во символов в заголовке - ${rule.maxTitleLength}`,
    });

  return true;
};

const checkTitleStringsLength = (value, { createError }) => {
  if (!value || !checkTitleStrings(value)) return true;
  const strings = getStrings(value);
  const rule = framesRules[NUMBER_CORRECT_FRAMES_RULE];
  for (const string of strings) {
    if (string.length >= rule.maxTitleLength) {
      return createError({
        message: `Максимальное кол-во символов в строке заголовка - ${rule.maxTitleLength}`,
      });
    }
    if (string.length < rule.minLength) {
      return createError({
        message: `Пустая строка`,
      });
    }
  }
  return true;
};

const checkFileFormat = (value) => {
  if (!value) return true;
  return SUPPORTED_FORMATS.includes(value.type);
};

const checkFileSize = (value) => {
  if (!value) return true;
  return value.size <= MAX_IMAGE_SIZE * 1024;
};

const checkFileSides = (value) => {
  if (!value) return true;
  return checkImageSides(value).then(
    (image) =>
      image.width <= MAX_IMAGE_WIDTH && image.height <= MAX_IMAGE_HEIGHT,
  );
};

export const storyValidationSchema = Yup.object({
  stories: Yup.array().of(
    Yup.object().shape({
      previewTitle: Yup.string(),
      previewUrl: Yup.mixed()
        .test("fileFormat", "Неподходящий тип изображения", checkFileFormat)
        .test(
          "fileSize",
          `Максимальный допустимый размер - ${MAX_IMAGE_SIZE}КБ`,
          checkFileSize,
        )
        .test(
          "fileSides",
          `Максимальный допустимый размер - ${MAX_IMAGE_WIDTH}x${MAX_IMAGE_HEIGHT}px`,
          checkFileSides,
        ),
      storyFrames: Yup.array().of(
        Yup.object().shape({
          title: Yup.string()
            .required("Поле обязательно")
            .test(
              "titleStrings",
              `Максимальное кол-во строк в заголовке - ${framesRules.length}`,
              checkTitleStrings,
            )
            .test("titleLength", checkTitleLength)
            .test("titleStringsLength", checkTitleStringsLength),
          text: Yup.string()        
          .notRequired()
          .when('title', (titleValue, textSchema) => {
            if (!titleValue || !checkTitleStrings(titleValue)) return textSchema;
            const titleStringsCount = getStrings(titleValue).length;
            const rule = framesRules[titleStringsCount - 1];

              return textSchema.test(
                "textValidation",
                (value, { createError }) => {
                  if (!value) return true;

                  if (!checkStringsCount(value, rule.maxTextStrings))
                    return createError({
                      message: `Максимальное кол-во строк в тексте - ${rule.maxTextStrings}`,
                    });

                  if (!checkLength(value, rule.maxTextLength))
                    return createError({
                      message: `Максимальное кол-во символов в тексте - ${rule.maxTextLength}`,
                    });
                  const strings = getStrings(value);
                  for (const string of strings) {
                    if (string.length < rule.minLength) {
                      return createError({
                        message: `Пустая строка`,
                      });
                    }
                  }

                  return true;
                },
              );
            }),
          pictureUrl: Yup.mixed()
            .nullable()
            .required("Поле обязательно")
            .test("fileFormat", "Неподходящий тип изображения", checkFileFormat)
            .test(
              "fileSize",
              `Максимальный допустимый размер - ${MAX_IMAGE_SIZE}КБ`,
              checkFileSize,
            )
            .test(
              "fileSides",
              `Максимальный допустимый размер - ${MAX_IMAGE_WIDTH}x${MAX_IMAGE_HEIGHT}px`,
              checkFileSides,
            ),
          linkText: Yup.string().when("visibleButtonOrNone", {
            is: "LINK",
            then: Yup.string().required("Поле обязательно"),
          }),
          linkUrl: Yup.string().when("visibleButtonOrNone", {
            is: "LINK",
            then: Yup.string().required("Поле обязательно"),
          }),
          buttonText: Yup.string().when("visibleButtonOrNone", {
            is: "BUTTON",
            then: Yup.string().required("Поле обязательно"),
          }),
          buttonUrl: Yup.string().when("visibleButtonOrNone", {
            is: "BUTTON",
            then: Yup.string().required("Поле обязательно"),
          }),
        }),
      ),
    }),
  ),
});
