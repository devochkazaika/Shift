import React, {useState, useEffect} from 'react';
import { FieldArray, Form, Formik } from 'formik';
import FormField from "../../FormField";
import Button from '../.././ui/Button';
import ColorPicker from './../../ColorPicker/index';
import { gradientOptions } from './../../../utils/constants/gradient';
import { updateFrame, fetchImage } from '../../../api/stories';
import UploadImage from './../../UploadImage/index';

const StoryFrame = ({ story, frame, frameIndex, storyIndex, platform, ...props }) => {
  const handleOnSubmit = async (values, platform, frame, frameId) => {
    updateFrame(values, platform, frame, storyIndex, frameId, frameIndex);
  };
  const [initialImage, setInitialImage] = useState(null);
  useEffect(() => {
    //получаем картинку
    if (!initialImage) {
      fetchImage(frame.pictureUrl, setInitialImage);
    }

  }, [frame.pictureUrl])

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
          buttonUrl: frame.buttonUrl,
          [`pictureFrame_${storyIndex}_${frameIndex}`]: initialImage
        }}
        onSubmit={(values) => handleOnSubmit(values, platform, frame, frameIndex)}
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
                    {...props}
                  />
                  <div className='row'>
                    <FormField
                      name={`text`}
                      value={values.text}
                      labelTitle="Текст"
                      as={"textarea"}
                      type="text"
                      onChange={handleChange}
                      {...props}
                    />
                    <FormField
                      name={`textColor`}
                      labelTitle="Цвет текста"
                      value={values.textColor}
                      component={ColorPicker}
                      onChange={handleChange}
                      {...props}
                    />
                  </div>
                  <FormField
                    name={`gradient`}
                    labelTitle="Градиент"
                    value={values.gradient}
                    as="select"
                    options={gradientOptions}
                    onChange={handleChange}
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
                          onChange={handleChange}
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
                          onChange={handleChange}
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
                            onChange={handleChange}
                            {...props}
                          />
                        </div>
                        <div className='column'>
                          <FormField
                            name={`buttonTextColor`}
                            labelTitle="Цвет текста"
                            component={ColorPicker}
                            value={values.buttonTextColor}
                            onChange={handleChange}
                            {...props}
                          />
                          <FormField
                            name={`buttonBackgroundColor`}
                            labelTitle="Цвет кнопки"
                            component={ColorPicker}
                            value={values.buttonBackgroundColor}
                            onChange={handleChange}
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
                          onChange={handleChange}
                          {...props}
                        />
                      </div>
                    </>
                  )}
                  <div className='row'>
                    <FormField
                        labelTitle={"Картинка"}
                        name={`pictureFrame_${storyIndex}_${frameIndex}`}
                        component={UploadImage}
                        {...props}
                    />
                    <div className='row'>
                      <div className="input_field">
                        <Button
                          handleOnClick={() => handleOnSubmit(story, platform, values, frame.id)}
                          text="Изменить"
                          type="button"
                          color="green"
                        />
                      </div>
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