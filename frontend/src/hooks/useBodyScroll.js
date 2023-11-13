import React from 'react';
import { disableBodyScroll, enableBodyScroll } from 'body-scroll-lock';

const useBodyScroll = () => {
  React.useEffect(() => {
    disableBodyScroll(document);
    return () => enableBodyScroll(document);
  }, []);
};

export default useBodyScroll;
