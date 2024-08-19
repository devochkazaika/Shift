import React from "react";
import FormField from "../FormField";

const WebsiteSectionBanner = () => {
  return (
    <div>
         <div className="row"><label htmlFor={`ButtonIntarectiveType`}>
         Всем пользователям
        <FormField
          name={`visibleButtonOrNone`}
          value="BUTTON"
          id={`ButtonIntarectiveType`}
          type="radio"
        />
      </label></div>
      <div className="row"><label htmlFor={`ButtonIntarectiveType`}>
         Активен
        <FormField
          name={`visibleButtonOrNone`}
          value="BUTTON"
          id={`ButtonIntarectiveType`}
          type="radio"
        />
      </label></div>
      

    </div>
  );
};

export default WebsiteSectionBanner;
