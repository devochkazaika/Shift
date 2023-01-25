import React from 'react';
import { Field, ErrorMessage } from 'formik';

const FormField = ({ labelTitle, name, type, component, as, options }) => {
  return (
    <div>
      <label htmlFor={name}>{labelTitle}</label>
      <Field name={name} placeholder="" type={type} component={component} as={as}>
        {options &&
          options.map((option, i) => (
            <option key={i} value={option.value}>
              {option.name}
            </option>
          ))}
      </Field>
      <ErrorMessage name={name} component="div" className="field-error" />
    </div>
  );
};

export default FormField;
