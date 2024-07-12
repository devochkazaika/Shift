import React from "react";
import FormField from "../FormField";
import { banks } from "../../utils/constants/banks";

const CommonForm = ( {setBankId, setPlatform, ...props }) => {
  function selectedBankId(event){
    const selectedBankId = event.target.value;
      setBankId(selectedBankId);
      props.handleChange(event);
  }

  return (
    <>
      <h2>Банк</h2>
      <div className="input_field">
        {setBankId(props.values.bankId)}
        <FormField
          name={`bankId`}
          as="select"
          onChange={(event) => selectedBankId(event)}
          options={banks.map((bank) => {
            return { value: bank.id, name: bank.name };
          })}
          {...props}
        />
      </div>

      <h2>Платформа</h2>
      <div role="group" aria-labelledby="my-radio-group">
        <div className="row">
          {setPlatform(props.values.platformType)}
          <label htmlFor="AllPlatformType">
            <FormField
              labelTitle="Все платформы"
              id="AllPlatformType"
              type="radio"
              name={`platformType`}
              value="ALL PLATFORMS"
              checked={props.values.platformType === "ALL PLATFORMS"}
              onChange={(event) => selectedBankId(event)}
              {...props}
            />
          </label>

          <label htmlFor="AndroidPlatformType">
            {setPlatform(props.values.platformType)}
            <FormField
              labelTitle="Android"
              id="AndroidPlatformType"
              type="radio"
              name={`platformType`}
              value="ANDROID"
              onChange={(event) => selectedBankId(event)}
              checked={props.values.platformType === "ANDROID"}
              {...props}
            />
          </label>

          <label htmlFor="IOSPlatformType">
            {setPlatform(props.values.platformType)}
            <FormField
              labelTitle="IOS"
              id="IOSPlatformType"
              type="radio"
              name={`platformType`}
              value="IOS"
              onChange={(event) => selectedBankId(event)}
              checked={props.values.platformType === "IOS"}
              {...props}
            />
          </label>
          <label htmlFor="WebPlatformType">
            {setPlatform(props.values.platformType)}
            <FormField
              labelTitle="Web"
              id="WebPlatformType"
              type="radio"
              name={`platformType`}
              value="WEB"
              onChange={(event) => selectedBankId(event)}
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
