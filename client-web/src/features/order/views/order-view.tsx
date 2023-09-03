import React, { type FC, memo } from "react";
import { Order } from "../components/order";

export const OrderView: FC = () => (
    <Order />
);

// eslint-disable-next-line import/no-default-export
export default memo(() => (
    <OrderView />
));
