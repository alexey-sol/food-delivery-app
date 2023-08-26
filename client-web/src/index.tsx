import React from "react";
import { createRoot } from "react-dom/client";
import { AppWithProviders } from "app";
import { getRootElement } from "shared/utils/helpers/dom";

const root = createRoot(getRootElement());

root.render(<AppWithProviders />);
