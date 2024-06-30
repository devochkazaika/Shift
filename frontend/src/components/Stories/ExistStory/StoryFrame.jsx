import React from 'react';
import {FieldArray, Form, Formik } from 'formik';
import FormField from "../../FormField";
// import AlertMessage from './../../ui/AlertMessage/index';
// import Button from './../../ui/Button/index';
import { initialStoryValues } from "../../../utils/constants/initialValues";
// import styles from "../StoryFormParts/StoryFormParts.module.scss";
import ColorPicker from './../../ColorPicker/index';


const StoryFrame = ({ frame }) => {
  return (
    <div>
      <Formik
          enableReinitialize
          initialValues={initialStoryValues}
        >
          {() => (
            <div>
              <Form>
                <FieldArray name="stories">
                {(props) => (
                  <div>
                    <div>
                      <FormField
                              labelTitle={"Tittle"}
                              change
                              as={"input"}
                              type="text"
                              {...props}
                              />
                      <FormField
                              labelTitle={"Text"}
                              change
                              as={"input"}
                              type="text"
                              {...props}
                              />
                        <FormField
                          labelTitle={"Цвет текста"}
                          component={ColorPicker}
                          {...props}
                        />
                        <FormField
                              labelTitle={"Visible Link or Button or None:"}
                              change
                              as={"input"}
                              type="text"
                              {...props}
                              />
                        <FormField
                              labelTitle={"Button Text: "}
                              change
                              as={"input"}
                              type="text"
                              {...props}
                        />
                        <FormField
                              labelTitle={"Button Text Color:"}
                              change
                              as={"input"}
                              type="text"
                              {...props}
                        />
                        <FormField
                              labelTitle={"Button Background Color:"}
                              change
                              as={"input"}
                              type="text"
                              {...props}
                        />
                          
                        <img className='pict' src={"http://localhost:8080"+frame.pictureUrl} alt="Story Frame" />
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
