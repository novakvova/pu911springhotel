import "cropperjs/dist/cropper.min.css";
import * as React from "react";
import CropperModal from '../common/CropperModal';


const UploadingPage: React.FC = () => {
  const handleSelected = (base64: string) => {
    console.log("base64", base64);
  };

  return (
    <>
      <h1>Загрузка фоток</h1>
      <CropperModal onSelected={handleSelected} aspectRation={16 / 9} />
    </>
  );
};

export default UploadingPage;
