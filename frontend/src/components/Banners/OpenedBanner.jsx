/* eslint-disable */
import React from "react";
import FormField from "../FormField";
import UploadImage from "../UploadImage";
const OpenedBanner = ({ storyIndex, ...props }) => {
  return (
    <>
      <div className="input_field">
        <FormField
          className="title"
          labelTitle="Наименование"
          name={`stories.${storyIndex}.previewTitle`}
          as="textarea"
          {...props}
        />
      </div>
      <div className="input_field">
        <FormField
          className="title"
          labelTitle="Код"
          name={`stories.${storyIndex}.previewTitle`}
          as="textarea"
          {...props}
        />
      </div>
      <div className="input_field">
        <FormField
          labelTitle="Изображение"
          name={`stories.${storyIndex}.previewUrl`}
          component={UploadImage}
          {...props}
        />
      </div>
      <div className="input_field">
        <FormField
          labelTitle={"Текст"}
          name={`stories.${storyIndex}.text`}
          as={"textarea"}
          {...props}
        />
      </div>
    </>
  );
};

export default OpenedBanner;
