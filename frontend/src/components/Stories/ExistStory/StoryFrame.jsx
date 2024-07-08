import React, {useState, useEffect} from 'react';
import { FieldArray, Form, Formik, Field } from 'formik';
import FormField from "../../FormField";
import Button from '../.././ui/Button';
import ColorPicker from './../../ColorPicker/index';
import { gradientOptions } from './../../../utils/constants/gradient';
import { ReactComponent as ArrowIcon } from '../../../assets/icons/arrow-up.svg';
import { updateFrame } from '../../../api/stories';
import axios from 'axios';
import UploadImage from './../../UploadImage/index';

const StoryFrame = ({ story, frame, frameIndex, storyIndex, platform }) => {
  const handleOnSubmit = async (values, platform, frame, frameIndex) => {
    updateFrame(values, platform, frame, frameIndex);
  };
  const [initialImage, setInitialImage] = useState(null);
  const [imageLoadad, setImageLoaded] = useState(false);
  useEffect(() => {
    //получаем картинку
    const fetchImage = async () => {
      try {
        const response = await axios.get("http://localhost:8080" + frame.pictureUrl, { responseType: 'arraybuffer' });
        const blob = new Blob([response.data], { type: response.headers['content-type'] });
        const file = new File([blob], "initial_image.jpg", { type: blob.type });
        setInitialImage(file);
      } catch (error) {
        console.error('Error fetching the image:', error);
      }
    };
    if (!imageLoadad){
      fetchImage();
      setImageLoaded(true);
    }
  }, [imageLoadad, frame.pictureUrl])

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
          pictureFrame: initialImage
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
                    as={FormField}
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
                    <FormField
                        labelTitle={"Картинка"}
                        name={`pictureFrame`}
                        component={UploadImage}
                    />
                    <div className='item-card__button--change'>
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