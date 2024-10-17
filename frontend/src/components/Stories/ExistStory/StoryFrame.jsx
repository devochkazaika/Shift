import React, { useState, useEffect } from "react";
import { FieldArray, Form, Formik } from "formik";
import FormField from "../../FormField";
import Button from "../.././ui/Button";
import ColorPicker from "./../../ColorPicker/index";
import { gradientOptions } from "./../../../utils/constants/gradient";
import { updateFrame, fetchImage } from "../../../api/stories";
import UploadImage from "./../../UploadImage/index";
import { storyFrameValidationSchema } from "./../../../utils/helpers/validation";

const StoryFrame = ({
  story,
  frame,
  changeable,
  frameIndex,
  storyIndex,
  platform,
  changeStory,
  ...props
}) => {
  const handleOnSubmit = async (values, platform, frame, frameId) => {
    updateFrame(values, platform, frame, storyIndex, frameId, frameIndex);
  };
  const [initialImage, setInitialImage] = useState(null);
  useEffect(() => {
    //получаем картинку
    if (!initialImage) {
      fetchImage(frame.pictureUrl, setInitialImage);
    }
  }, [frame.pictureUrl]);

  const onChangeStory = (eventTarget, controlName, setFieldValue) => {
    setFieldValue(controlName, eventTarget.value);
    changeStory(eventTarget.value, storyIndex, frame.id, controlName);
  }

  return (
    <div>
      <Formik
        enableReinitialize
        validationSchema={storyFrameValidationSchema(storyIndex, frameIndex)}
        initialValues={{
          title: frame.title,
          text: frame.text,
          visibleButtonOrNone: frame.visibleButtonOrNone,
          gradient: frame.gradient,
          textColor: frame.textColor,
          buttonText: frame.buttonText,
          buttonTextColor: frame.buttonTextColor,
          buttonBackgroundColor: frame.buttonBackgroundColor,
          buttonUrl: frame.buttonUrl,
          [`pictureFrame_${storyIndex}_${frameIndex}`]: initialImage,
        }}
        onSubmit={(values) => handleOnSubmit(story, platform, values, frame.id)}
      >
        {({ values, setFieldValue }) => (
          
          <Form>
            <FieldArray name={`frames[${storyIndex}]`}>
              {() => (
                <div>
                  <FormField
                    labelTitle="Заголовок"
                    name="title"
                    value={values.title}
                    type="text"
                    onChange={(event) => onChangeStory(event.target.value, "title", setFieldValue)}
                    {...props}
                  />
                  <div className="row">
                    <FormField
                      name={`text`}
                      value={values.text}
                      labelTitle="Текст"
                      as={"textarea"}
                      type="text"
                      onChange={(event) => onChangeStory(event.target.value, "text", setFieldValue)}
                      {...props}
                    />
                    <FormField
                      name={`textColor`}
                      labelTitle="Цвет текста"
                      value={values.textColor}
                      component={ColorPicker}
                      onChange={(event) => onChangeStory(event.target.value, "textColor", setFieldValue)}
                      {...props}
                    />
                  </div>
                  <FormField
                    name={`gradient`}
                    labelTitle="Градиент"
                    value={values.gradient}
                    as="select"
                    options={gradientOptions}
                    onChange={(event) => onChangeStory(event.target.value, "gradient", setFieldValue)}
                    {...props}
                  />
                  <div role="group" aria-labelledby="my-radio-group">
                    <div className="row">
                      <label htmlFor={`ButtonIntarectiveType-${frameIndex}`}>
                        Кнопка
                        <FormField
                          name={`visibleButtonOrNone`}
                          value="BUTTON"
                          id={`ButtonIntarectiveType-${frameIndex}`}
                          type="radio"
                          checked={values.visibleButtonOrNone === "BUTTON"}
                          onChange={(event) => onChangeStory(event.target.value, "visibleButtonOrNone", setFieldValue)}
                          {...props}
                        />
                      </label>
                      <label htmlFor={`NonIntarectiveType-${frameIndex}`}>
                        Ничего
                        <FormField
                          name={`visibleButtonOrNone`}
                          value="NONE"
                          id={`NonIntarectiveType-${frameIndex}`}
                          type="radio"
                          checked={values.visibleButtonOrNone === "NONE"}
                          onChange={(event) => onChangeStory(event.target.value, "visibleButtonOrNone", setFieldValue)}
                          {...props}
                        />
                      </label>
                    </div>
                  </div>
                  {values.visibleButtonOrNone === "BUTTON" && (
                    <>
                      <div className="row">
                        <div className="input_field">
                          <FormField
                            name={`buttonText`}
                            labelTitle="Текст"
                            as="textarea"
                            value={values.buttonText}
                            onChange={(event) => {
                              onChangeStory(event.target.value, "buttonText", setFieldValue);
                              values.buttonText = event.target.value;
                            }}
                            {...props}
                          />
                        </div>
                        <div className="column">
                          <FormField
                            name={`buttonTextColor`}
                            labelTitle="Цвет текста"
                            component={ColorPicker}
                            value={values.buttonTextColor}
                            onChange={(event) => onChangeStory(event.target.value, "buttonTextColor", setFieldValue)}
                            {...props}
                          />
                          <FormField
                            name={`buttonBackgroundColor`}
                            labelTitle="Цвет кнопки"
                            component={ColorPicker}
                            value={values.buttonBackgroundColor}
                            onChange={(event) => onChangeStory(event.target.value, "buttonBackgroundColor", setFieldValue)}
                            {...props}
                          />
                        </div>
                      </div>
                      <div className="input_field">
                        <FormField
                          name={`buttonUrl`}
                          labelTitle="Ссылка"
                          type="text"
                          value={values.buttonUrl}
                          onChange={(event) => {
                            onChangeStory(event.target.value, "buttonUrl", setFieldValue)
                            values.buttonUrl = event.target.value;
                          }}
                          {...props}
                        />
                      </div>
                    </>
                  )}
                  <div className="row">
                    <FormField
                      labelTitle={"Картинка"}
                      name={`pictureFrame_${storyIndex}_${frameIndex}`}
                      component={UploadImage}
                      {...props}
                    />
                    {!changeable ? 
                    <div className="row">
                      <div className="input_field">
                        <Button text="Изменить" type="submit" color="green" />
                      </div>
                    </div>
                    : <></>
                    }
                  </div>
                </div>
              )}
            </FieldArray>
          </Form>
        )}
      </Formik>
    </div>
  );
};

export default StoryFrame;
