import React from "react";
import FormField from "../FormField";
import { banks } from "../../utils/constants/banks";

const CommonForm = ( {setBankId, setPlatform, ...props }) => {

  return (
    <>
      <h2>Банк</h2>
      <div className="input_field">
        <FormField
          name={`bankId`}
          as="select"
          onChange={setBankId(props.values.bankId)}
          options={banks.map((bank) => {
            return { value: bank.id, name: bank.name };
          })}
          {...props}
        />
      </div>

      <h2>Платформа</h2>
      <div role="group" aria-labelledby="my-radio-group">
        <div className="row">
          <label htmlFor="AllPlatformType">
            <FormField
              labelTitle="Все платформы"
              id="AllPlatformType"
              type="radio"
              name={`platformType`}
              value="ALL PLATFORMS"
              checked={props.values.platformType === "ALL PLATFORMS"}
              onChange={setPlatform(props.values.platformType)}
              {...props}
            />
          </label>

          <label htmlFor="AndroidPlatformType">
            <FormField
              labelTitle="Android"
              id="AndroidPlatformType"
              type="radio"
              name={`platformType`}
              value="ANDROID"
              onChange={setPlatform(props.values.platformType)}
              checked={props.values.platformType === "ANDROID"}
              {...props}
            />
          </label>

          <label htmlFor="IOSPlatformType">
            <FormField
              labelTitle="IOS"
              id="IOSPlatformType"
              type="radio"
              name={`platformType`}
              value="IOS"
              onChange={setPlatform(props.values.platformType)}
              checked={props.values.platformType === "IOS"}
              {...props}
            />
          </label>
          <label htmlFor="WebPlatformType">
            <FormField
              labelTitle="Web"
              id="WebPlatformType"
              type="radio"
              name={`platformType`}
              value="WEB"
              onChange={setPlatform(props.values.platformType)}
              checked={props.values.platformType === "WEB"}
              {...props}
            />
          </label>
        </div>
      </div>
    </>
  );
};

export default CommonForm;
