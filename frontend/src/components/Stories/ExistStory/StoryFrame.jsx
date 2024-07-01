import React from 'react';
import {FieldArray, Form, Formik, Field } from 'formik';
import FormField from "../../FormField";
// import AlertMessage from './../../ui/AlertMessage/index';
// import Button from './../../ui/Button/index';
// import { initialStoryValues } from "../../../utils/constants/initialValues";
// import styles from "../StoryFormParts/StoryFormParts.module.scss";
import ColorPicker from './../../ColorPicker/index';

const StoryFrame = ({ frame, frameIndex, storyIndex }) => {
  // const [patchFrame, setFrame] = React.useState(frame)

  return (
    <div>
      <Formik
        enableReinitialize
        initialValues={{
          title: frame.title,
          text: frame.text,
          visibleButtonOrNone: frame.visibleButtonOrNone,
          // Другие начальные значения
        }}
        onSubmit={(values) => {
          // Обработка отправки формы
          console.log(values);
        }}
      >
        {({ values, handleChange }) => (
          <div>
            <Form>
              <FieldArray name="stories">
                {(props) => (
                  <div>
                    <div>
                      <Field
                        labelTitle={"Заголовок"}
                        name={`title`}
                        value={values.title}
                        as={FormField}
                        type="text"
                        onChange={handleChange}
                      />
                      <div className='row'>
                        <Field
                          name={`text`}
                          value={values.text}
                          labelTitle={"Текст"}
                          as={FormField}
                          type="text"
                          onChange={handleChange}
                        />
                        <FormField
                          name={`stories.${storyIndex}.storyFrames.${frameIndex}.textColor`}
                          labelTitle={"Цвет текста"}
                          component={ColorPicker}
                          onChange={handleChange}
                          {...props}
                        />
                      </div>
                      <div role="group" aria-labelledby="my-radio-group">
                        <div className="row">
                          <label htmlFor="ButtonIntarectiveType">
                            Кнопка
                            <Field
                              name={`visibleButtonOrNone`}
                              value={"BUTTON"}
                              as={FormField}
                              id="ButtonIntarectiveType"
                              type="radio"
                              checked={values.visibleButtonOrNone === "BUTTON"}
                              onChange={handleChange}
                            />
                          </label>
                          <label htmlFor="NonIntarectiveType">
                            Ничего
                            <Field
                              name={`visibleButtonOrNone`}
                              value={"NONE"}
                              as={FormField}
                              id="ButtonIntarectiveType"
                              type="radio"
                              checked={values.visibleButtonOrNone === "NONE"}
                              onChange={handleChange}
                            />
                          </label>
                        </div>
                      </div>
                      {values.visibleButtonOrNone === "BUTTON" && (
                        <>
                          {console.log(frame.visibleLinkOrButtonOrNone)}
                          <div className="row">
                            <div className="input_field">
                              <FormField
                                name={`stories.${storyIndex}.storyFrames.${frameIndex}.buttonText`}
                                labelTitle={"Текст"}
                                as={"textarea"}
                                onChange={handleChange}
                                {...props}
                              />
                            </div>
                            <FormField
                              name={`stories.${storyIndex}.storyFrames.${frameIndex}.buttonTextColor`}
                              labelTitle={"Цвет текста"}
                              component={ColorPicker}
                              onChange={handleChange}
                              {...props}
                            />
                          </div>
                          <FormField
                            name={`stories.${storyIndex}.storyFrames.${frameIndex}.buttonColor`}
                            labelTitle={"Цвет кнопки"}
                            component={ColorPicker}
                            onChange={handleChange}
                            {...props}
                          />
                          <div className="input_field">
                            <FormField
                              name={`stories.${storyIndex}.storyFrames.${frameIndex}.buttonLink`}
                              labelTitle={"Ссылка"}
                              type="text"
                              onChange={handleChange}
                              {...props}
                            />
                          </div>
                        </>
                      )}
                      <img className='pict' src={`http://localhost:8080${frame.pictureUrl}`} alt="Story Frame" />
                    </div>
                  </div>
                )}
              </FieldArray>
            </Form>
          </div>
        )}
      </Formik>
    </div>
  );
}

export default StoryFrame;

