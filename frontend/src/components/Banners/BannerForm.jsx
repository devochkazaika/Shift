/* eslint-disable */
import React from "react";
import MainBannerFields from "./MainBannerFields";
import OpenedBanner from "./OpenedBanner";
import WebsiteSectionBanner from "./WebsiteSectionBanner";

const BannerForm = ({ storyIndex, storyJson, ...props }) => {
  return (
    <div>
      <h2>Основной баннер</h2>
      <MainBannerFields />
      <br />
      <h2>Открытый баннер</h2>
      <OpenedBanner/>
      <h2>Раздел сайта</h2>
      <WebsiteSectionBanner/>
    </div>
  );
};

export default BannerForm;
