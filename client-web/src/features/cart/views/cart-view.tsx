import React, { type FC, memo } from "react";

import { Cart } from "../components/cart";

export const CartView: FC = () => (
    <Cart />
);

// eslint-disable-next-line import/no-default-export
export default memo(() => (
    <CartView />
));
