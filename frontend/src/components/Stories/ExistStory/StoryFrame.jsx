import React, { useState, useEffect } from "react";
import {FieldArray, Form, Formik } from "formik";
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
                    onChange={(e) => {
                      setFieldValue('title', e.target.value); // Правильное использование setFieldValue
                      changeStory(e.target.value, storyIndex, frame.id, "title"); // Ваша дополнительная логика
                    }}
                    {...props}
                  />
                  <div className="row">
                    <FormField
                      name={`text`}
                      value={values.text}
                      labelTitle="Текст"
                      as={"textarea"}
                      type="text"
                      onChange={(e) => {
                        setFieldValue(e);
                        values.text = e.target.value;
                        changeStory(e.target.value, storyIndex, frame.id, "text");
                      }}
                      {...props}
                    />
                    <FormField
                      name={`textColor`}
                      labelTitle="Цвет текста"
                      value={values.textColor}
                      component={ColorPicker}
                      onChange={(e) => {
                        setFieldValue(e);
                        values.textColor = e.target.value;
                        changeStory(e.target.value, storyIndex, frame.id, "textColor");
                      }}
                      {...props}
                    />
                  </div>
                  <FormField
                    name={`gradient`}
                    labelTitle="Градиент"
                    value={values.gradient}
                    as="select"
                    options={gradientOptions}
                    onChange={(e) => {
                      setFieldValue(e);
                      values.gradient = e.target.value;
                      changeStory(e.target.value, storyIndex, frame.id, "gradient");
                    }}
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
                          onChange={(e) => {
                            setFieldValue(e);
                            values.visibleButtonOrNone = e.target.value;
                            changeStory(e.target.value, storyIndex, frame.id, "visibleButtonOrNone");
                          }}
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
                          onChange={(e) => {
                            setFieldValue(e);
                            values.visibleButtonOrNone = e.target.value;
                            changeStory(e.target.value, storyIndex, frame.id, "visibleButtonOrNone");
                          }}
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
                            onChange={(e) => {
                              setFieldValue(e);
                              values.buttonText = e.target.value;
                              changeStory(e.target.value, storyIndex, frame.id, "buttonText");
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
                            onChange={(e) => {
                              setFieldValue(e);
                              values.buttonTextColor = e.target.value;
                              changeStory(e, storyIndex, frame.id, "buttonTextColor");
                            }}
                            {...props}
                          />
                          <FormField
                            name={`buttonBackgroundColor`}
                            labelTitle="Цвет кнопки"
                            component={ColorPicker}
                            value={values.buttonBackgroundColor}
                            onChange={(e) => {
                              setFieldValue(e);
                              values.buttonBackgroundColor = e.target.value;
                              changeStory(e.target.value, storyIndex, frame.id, "buttonBackgroundColor");
                            }}
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
                          onChange={(e) => {
                            setFieldValue(e);
                            values.buttonUrl = e.target.value;
                            changeStory(e.target.value, storyIndex, frame.id, "buttonUrl");
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
