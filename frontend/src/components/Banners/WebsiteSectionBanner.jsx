/* eslint-disable */
import React from "react";
import FormField from "../FormField";
import { webSectionsOptions } from "../../utils/constants/webSectionsOptions";

const WebsiteSectionBanner = () => {
  return (
    <div>
      <div className="input_field">
        <FormField name={`gradient`} as="select" options={webSectionsOptions} />
      </div>
      <div className="input_field">
        <FormField
          labelTitle="Приоритет"
          name={`stories.buttonUrl`}
          type="text"
        />
      </div>
      <div className="row">
        <FormField
          labelTitle="Всем пользователям"
          name={`visibleButtonOrNone`}
          value="BUTTON"
          id={`ButtonIntarectiveType`}
          type="checkbox"
        />
      </div>
      <div className="row">
        <FormField
          labelTitle="Активен"
          name={`visibleButtonOrNone`}
          value="BUTTON"
          id={`ButtonIntarectiveType`}
          type="checkbox"
        />
      </div>
    </div>
  );
};

export default WebsiteSectionBanner;
