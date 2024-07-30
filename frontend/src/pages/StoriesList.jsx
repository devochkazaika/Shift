/* eslint-disable  */
import React, { useEffect, useState } from "react";

import { uploadStories } from "../api/stories";
import { initialStoryValues } from "../utils/constants/initialValues";
import { convertToPayload } from "../utils/helpers/byteArrayFunctions";

import { ReactComponent as ArrowIcon } from "../assets/icons/arrow-up.svg";

import Loader from "../components/ui/Loader/index";
import StoryPanel from "../components/Stories/ExistStory/StoryPanel";
import axios from "axios";
import Button from "../components/ui/Button";
import { useNavigate } from "react-router-dom";

const StoriesList = () => {
  const navigate = useNavigate();
  const [send, setSend] = useState(false);
  const [success, setSuccess] = useState(true);
  const [loading, setLoading] = useState(false);
  const [allPlatformsArray, setAllPlatformsArray] = useState([]);
  const [webArray, setWebArray] = useState([]);
  const [androidArray, setAndroidArray] = useState([]);
  const [iosArray, setIosArray] = useState([]);

  const [bankId, setBankId] = useState("absolutbank");
  const [platform, setPlatform] = useState("ALL PLATFORMS");

  const fetchData = async (bankId, platform) => {
    try {
      const response = await axios.get(
        `/stories/bank/info/getJson/?bankId=${bankId}&platform=${platform}`,
        {
          headers: {},
          responseType: "json",
        },
      );
      return response.data;
    } catch (error) {
      console.error("Error fetching data:", error);
      return null;
    }
  };

  useEffect(() => {
    const fetchDataAsync = async () => {
      try {
        const data = await fetchData(bankId, "WEB");
        setWebArray(data || []);
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };
    fetchDataAsync();
  }, [bankId, platform]);

  useEffect(() => {
    const fetchDataAsync = async () => {
      try {
        const data = await fetchData(bankId, "ALL PLATFORMS");
        setAllPlatformsArray(data || []);
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };
    fetchDataAsync();
  }, [bankId, platform]);

  useEffect(() => {
    const fetchDataAsync = async () => {
      try {
        const data = await fetchData(bankId, "ANDROID");
        setAndroidArray(data || []);
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };
    fetchDataAsync();
  }, [bankId, platform]);

  useEffect(() => {
    const fetchDataAsync = async () => {
      try {
        const data = await fetchData(bankId, "IOS");
        setIosArray(data || []);
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };
    fetchDataAsync();
  }, [bankId, platform]);

  const handleOnSubmit = async (values, { resetForm }) => {
    setSend(false);
    setLoading(true);
    try {
      const payload = await convertToPayload(values);
      const previewImage = values.stories[0].previewUrl;
      const cardImages = values.stories[0].storyFrames.map((storyFrame) => {
        return storyFrame.pictureUrl;
      });
      const jsonPayload = JSON.stringify(payload, null, 2);
      const uploadResult = await uploadStories(
        jsonPayload,
        previewImage,
        cardImages,
      );
      setSuccess(uploadResult);
      if (uploadResult) {
        resetForm(initialStoryValues);
      }
    } catch (error) {
      console.error("Error uploading stories:", error);
    } finally {
      setSend(true);
      setLoading(false);
    }
  };

  return (
    <>
      <h1>Stories</h1>
      <div className="stories">
        <Button
          text="Добавить"
          type="button"
          color="green"
          icon={<ArrowIcon width="12px" height="12px" />}
          handleOnClick={() => navigate("/stories")}
        />
      </div>
      {allPlatformsArray.length > 0 ? (
        <>
          <StoryPanel storyArray={allPlatformsArray} platform={platform} />
        </>
      ) : (
        <></>
      )}
      {webArray.length > 0 ? (
        <StoryPanel storyArray={webArray} platform={"WEB"} />
      ) : (
        <></>
      )}
      {androidArray.length > 0 ? (
        <StoryPanel storyArray={androidArray} platform={"Android"} />
      ) : (
        <></>
      )}
      {iosArray.length > 0 ? (
        <StoryPanel storyArray={iosArray} platform={"IOS"} />
      ) : (
        <></>
      )}
      {loading && <Loader />}
    </>
  );
};

export default StoriesList;
