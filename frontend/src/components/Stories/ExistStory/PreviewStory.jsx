import React from "react";
import { Formik, Form, FieldArray } from "formik";
import FormField from "../../FormField";
import { storyPanelValidationSchema } from "../../../utils/helpers/validation";
import Button from "../../ui/Button";
import ColorPicker from "../../ColorPicker";
import UploadImage from "../../UploadImage";
import { gradientOptions } from "../../../utils/constants/gradient";
import styles from "./StoryCard.module.scss";
import { updateStory } from "../../../api/stories";

/**
 * @param {Object} props - Component props
 * @param {Object} story - Story data
 * @param {number} storyIndex - Index of the story
 * @param {string} initialImage - Initial preview image URL
 * @param {boolean} changeable - Flag to allow changes
 * @param {string} platform - Platform type
 */
const PreviewStory = ({ story, storyIndex, initialImage, changeable, platform }) => {
  return (
    <Formik
      enableReinitialize
      validationSchema={storyPanelValidationSchema(storyIndex)}
      initialValues={{
        previewTitle: story.previewTitle,
        previewTitleColor: story.previewTitleColor,
        previewGradient: story.previewGradient,
        [`previewUrl_${storyIndex}`]: initialImage,
      }}
      onSubmit={(values) => {
        updateStory(story, storyIndex, values, platform);
      }}
    >
      {({ values, setFieldValue }) => (
        <Form>
          <FieldArray name="frames">
            {() => (
              <div className={`row ${styles.wrapper}`}>
                <div className={styles.heading_input_size}>
                  <h3>Превью</h3>
                  <FormField
                    labelTitle="Заголовок"
                    name="previewTitle"
                    type="text"
                    value={values.previewTitle}
                    onChange={(e) => setFieldValue('previewTitle', e.target.value)}
                  />
                  <FormField
                    name="previewTitleColor"
                    labelTitle="Цвет заголовка"
                    value={values.previewTitleColor}
                    component={ColorPicker}
                    onChange={(e) => setFieldValue('previewTitleColor', e.target.value)}
                  />
                  <FormField
                    name="previewGradient"
                    labelTitle="Градиент"
                    value={values.previewGradient}
                    onChange={(e) => setFieldValue('previewGradient', e.target.value)}
                    as="select"
                    options={gradientOptions}
                  />
                </div>
                <div className={styles.gradient_input_area}>
                  <FormField
                    name={`previewUrl_${storyIndex}`}
                    component={UploadImage}
                    type="file"
                  />
                  {!changeable && <Button text="Изменить" type="submit" color="green" />}
                </div>
              </div>
            )}
          </FieldArray>
        </Form>
      )}
    </Formik>
  );
};

export default PreviewStory;