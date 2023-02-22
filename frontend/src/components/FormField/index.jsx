import React from 'react';
import { Field, ErrorMessage, getIn } from 'formik';

import formFieldStyles from './FormField.module.scss';

function ifObjectHasField(object, fieldName) {
  if (getIn(object, fieldName)) {
    return true;
  }
  return false;
}

const FormField = (props) => {
  return (
    <div className={formFieldStyles.root}>
      {props.type === 'radio' ? (
        <>
          <label htmlFor={props.name} className={formFieldStyles.radio_title}>
            {props.labelTitle}
          </label>
          <Field name={props.name} type={props.type} value={props.value} checked={props.checked} />
        </>
      ) : (
        <>
          <label htmlFor={props.name} className={formFieldStyles.label_title}>
            {props.labelTitle}
          </label>
          <Field
            name={props.name}
            placeholder=""
            type={props.type}
            component={props.component}
            as={props.as}
            className={
              formFieldStyles.field +
              ' ' +
              (ifObjectHasField(props.errors, props.name) &&
              ifObjectHasField(props.touched, props.name)
                ? formFieldStyles.error
                : '')
            }>
            {props.options &&
              props.options.map((option, i) => (
                <option key={i} value={option.value}>
                  {option.name}
                </option>
              ))}
          </Field>
        </>
      )}
      <ErrorMessage name={props.name} component="div" className={formFieldStyles.field_error} />
    </div>
  );
};

export default FormField;
