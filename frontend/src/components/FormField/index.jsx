import { ErrorMessage, Field, getIn, useField } from 'formik';
import React from 'react';

import styles from './FormField.module.scss';

function ifObjectHasField(object, fieldName) {
  return getIn(object, fieldName);
}

const FormField = (props) => {
  const [field] = useField(props.name);
  
  return (
    <div className={styles.root}>
      <label
        htmlFor={props.name}
        className={props.type === 'radio' ? styles.radio_title : styles.label_title}>
        {props.labelTitle}
      </label>
      <Field
        name={props.name}
        placeholder=""
        type={props.type}
        component={props.component}
        as={props.as}
        value={props.value || field.value}
        checked={props.checked}
        className={`${props.type !== 'radio' && styles.field} ${
          ifObjectHasField(props.errors, props.name) && ifObjectHasField(props.touched, props.name)
            ? styles.error
            : ''
        } ${props.className}`}>
        {props.options &&
          props.options.map((option, i) => (
            <option key={i} value={option.value}>
              {option.name}
            </option>
          ))}
      </Field>
      <ErrorMessage name={props.name} component="div" className={styles.error_message} />
    </div>
  );
};

export default FormField;
