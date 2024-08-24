/* eslint-disable */
import React from "react";
import FormField from "../FormField";
import { webSectionsOptions } from "../../utils/constants/webSectionsOptions";

const WebsiteSectionBanner = () => {
  return (
    <div>
      <div className="input_field">
        <FormField
          name={`webSection`}
          as="select"
          options={webSectionsOptions}
        />
      </div>
      <div className="input_field">
        <FormField labelTitle="Приоритет" name={`priority`} type="text" />
      </div>
      <div className="row">
        <FormField
          labelTitle="Всем пользователям"
          name={`isAllUsers`}
          value="allUsers"
          type="checkbox"
        />
      </div>
      <div className="row">
        <FormField
          labelTitle="Активен"
          name={`isBannerActive`}
          value="active"
          id={`ButtonIntarectiveType`}
          type="checkbox"
        />
      </div>
    </div>
  );
};

export default WebsiteSectionBanner;
