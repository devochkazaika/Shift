/* eslint-disable */
import React from "react";
import FormField from "../FormField";
import UploadImage from "../UploadImage";

const MainBannerFields = ({ storyIndex, ...props }) => {
  return (
    <>
      <div className="input_field">
        <FormField
          className="title"
          labelTitle="Наименование"
          name={`mainBannerTitle`}
          as="textarea"
          {...props}
        />
      </div>
      <div className="input_field">
        <FormField
          className="title"
          labelTitle="Код"
          name={`mainBannerCode`}
          as="textarea"
          {...props}
        />
      </div>
      <div className="input_field">
        <FormField
          labelTitle="Изображение"
          name={`mainBanner.previewUrl`}
          component={UploadImage}
          {...props}
        />
      </div>
      <div className="input_field">
        <FormField
          labelTitle="Ссылка"
          name={`mainBanner.buttonUrl`}
          type="text"
          {...props}
        />
      </div>
    </>
  );
};

export default MainBannerFields;
