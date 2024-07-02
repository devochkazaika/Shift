import React from 'react';
import { FieldArray, Form, Formik, Field } from 'formik';
import FormField from "../../FormField";
import Button from '../.././ui/Button';
import ColorPicker from './../../ColorPicker/index';
import { gradientOptions } from './../../../utils/constants/gradient';
import { ReactComponent as ArrowIcon } from '../../../assets/icons/arrow-up.svg';
import { updateFrame } from '../../../api/stories';

const StoryFrame = ({ story, frame, frameIndex, storyIndex, platform }) => {
  const handleOnSubmit = async (values, platform, frame, frameIndex) => {
    updateFrame(values, platform, frame, frameIndex);
  };

  return (
    <div>
      <Formik
        enableReinitialize
        initialValues={{
          title: frame.title,
          text: frame.text,
          visibleButtonOrNone: frame.visibleButtonOrNone,
          gradient: frame.gradient,
          textColor: frame.textColor,
          buttonText: frame.buttonText,
          buttonTextColor: frame.buttonTextColor,
          buttonBackgroundColor: frame.buttonBackgroundColor,
          buttonUrl: frame.buttonUrl
        }}
        onSubmit={(values) => handleOnSubmit(values, platform, frameIndex)}
      >
        {({ values, handleChange }) => (
          <Form>
            <FieldArray name={`frames[${storyIndex}]`}>
              {() => (
                <div>
                  <Field
                    labelTitle="Заголовок"
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
                      labelTitle="Текст"
                      as={FormField}
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
                      <label htmlFor={`ButtonIntarectiveType-${frameIndex}`}>
                        Кнопка
                        <Field
                          name={`visibleButtonOrNone`}
                          value="BUTTON"
                          as={FormField}
                          id={`ButtonIntarectiveType-${frameIndex}`}
                          type="radio"
                          checked={values.visibleButtonOrNone === "BUTTON"}
                          onChange={handleChange}
                        />
                      </label>
                      <label htmlFor={`NonIntarectiveType-${frameIndex}`}>
                        Ничего
                        <Field
                          name={`visibleButtonOrNone`}
                          value="NONE"
                          as={FormField}
                          id={`NonIntarectiveType-${frameIndex}`}
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
                  <div className='item-card__summary'>
                    <img className='pict' src={`http://localhost:8080${frame.pictureUrl}`} alt="Story Frame" />
                    <div className='item-card__button--change'>
                      {console.log(frame.id)}
                      <Button
                        handleOnClick={() => handleOnSubmit(story, platform, values, frame.id)}
                        text="Изменить"
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

export default StoryFrame;