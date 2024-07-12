import React from 'react';
import { FieldArray, Form, Formik, Field } from 'formik';
import FormField from "../../FormField";
import Button from '../.././ui/Button';
import ColorPicker from './../../ColorPicker/index';
import { gradientOptions } from './../../../utils/constants/gradient';
import { ReactComponent as ArrowIcon } from '../../../assets/icons/arrow-up.svg';
import { addFrame } from '../../../api/stories';
import UploadImage from './../../UploadImage/index';

const AddFrame = ({setFrames, frames, story, storyIndex, platform }) => {
  const handleOnSubmit = async (story, values, platform) => {
    try {
      const frame = await addFrame(story, values, platform);
      const updatedFrames = [...frames, frame.data];
      setFrames(updatedFrames);
    } catch (error) {
      console.error('Error adding frame:', error);
    }
  };

  return (
    <div>
      <Formik
        enableReinitialize
        initialValues={{
          title: "Sample Title",
          text: "Sample text for the story.",
          visibleButtonOrNone: "BUTTON",
          gradient: "EMPTY",
          textColor: "#ffff",
          buttonText: "Click Here",
          buttonTextColor: "#FFFFFF",
          buttonBackgroundColor: "#0000FF",
          buttonUrl: "https://example.com",
          pictureUrl: null
        }}
        onSubmit={(values) => handleOnSubmit(story, values, platform)}
      >
        {({ values, handleChange }) => (
          <Form>
            <FieldArray name={`frames[${storyIndex}]`}>
              {() => (
                <div>
                  <FormField
                    labelTitle="Заголовок"
                    name={`title`}
                    value={values.title}
                    type="text"
                    onChange={handleChange}
                  />
                  <div className='row'>
                    <FormField
                      name={`text`}
                      value={values.text}
                      labelTitle="Текст"
                      as={"textarea"}
                      type="text"
                      onChange={handleChange}
                    />
                    <FormField
                      name={`textColor`}
                      labelTitle="Цвет текста"
                      value={values.textColor}
                      component={ColorPicker}
                      onChange={handleChange}
                    />
                  </div>
                  <FormField
                    name={`gradient`}
                    labelTitle="Градиент"
                    value={values.gradient}
                    as="select"
                    options={gradientOptions}
                    onChange={handleChange}
                  />
                  <div role="group" aria-labelledby="my-radio-group">
                    <div className="row">
                      <label htmlFor={`ButtonIntarectiveType`}>
                        Кнопка
                        <Field
                          name={`visibleButtonOrNone`}
                          value="BUTTON"
                          as={FormField}
                          id={`ButtonIntarectiveType`}
                          type="radio"
                          checked={values.visibleButtonOrNone === "BUTTON"}
                          onChange={handleChange}
                        />
                      </label>
                      <label htmlFor={`NonIntarectiveType`}>
                        Ничего
                        <Field
                          name={`visibleButtonOrNone`}
                          value="NONE"
                          as={FormField}
                          id={`NonIntarectiveType`}
                          type="radio"
                          checked={values.visibleButtonOrNone === "NONE"}
                          onChange={handleChange}
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
                            onChange={handleChange}
                          />
                        </div>
                        <div className='column'>
                          <FormField
                            name={`buttonTextColor`}
                            labelTitle="Цвет текста"
                            component={ColorPicker}
                            value={values.buttonTextColor}
                            onChange={handleChange}
                          />
                          <FormField
                            name={`buttonBackgroundColor`}
                            labelTitle="Цвет кнопки"
                            component={ColorPicker}
                            value={values.buttonBackgroundColor}
                            onChange={handleChange}
                          />
                        </div>
                      </div>
                      <div className="input_field">
                        <FormField
                          name={`buttonUrl`}
                          labelTitle="Ссылка"
                          type="text"
                          value={values.buttonUrl}
                          onChange={handleChange}
                        />
                      </div>
                    </>
                  )}
                  <div className='row'>
                    <div className="input_field">
                      <FormField
                        name={`pictureUrl`}
                        component={UploadImage}
                      />
                    </div>
                    <div>
                      <Button
                        handleOnClick={() => handleOnSubmit(story, values, platform)}
                        text="Добавить"
                        type="button"
                        color="green"
                        icon={<ArrowIcon width="12px" height="12px" />}
                      />
                    </div>
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

export default AddFrame;