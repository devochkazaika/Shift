import React from 'react';
import { Field, ErrorMessage } from 'formik';

import formFieldStyles from './FormField.module.scss';

const FormField = ({
  labelTitle,
  name,
  type,
  value,
  checked,
  component,
  as,
  options,
  handleClick,
}) => {
  return (
    <div className={formFieldStyles.root}>
      {type === 'radio' ? (
        <>
          <label htmlFor={name} className={formFieldStyles.radio_title}>
            {labelTitle}
          </label>
          <Field name={name} type={type} value={value} onChange={handleClick} checked={checked} />
        </>
      ) : (
        <>
          <label htmlFor={name} className={formFieldStyles.label_title}>
            {labelTitle}
          </label>
          <Field
            name={name}
            placeholder=""
            type={type}
            component={component}
            as={as}
            className={formFieldStyles.field}>
            {options &&
              options.map((option, i) => (
                <option key={i} value={option.value}>
                  {option.name}
                </option>
              ))}
          </Field>
        </>
      )}
      <ErrorMessage name={name} component="div" className="field-error" />
    </div>
  );
};

export default FormField;
