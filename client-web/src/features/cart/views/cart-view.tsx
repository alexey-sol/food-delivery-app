import React, { type FC, memo } from "react";
import { OrderProvider } from "features/order/contexts/order";
import { Cart } from "../components/cart";

export const CartView: FC = () => (
    <Cart />
);

// eslint-disable-next-line import/no-default-export
export default memo(() => (
    <OrderProvider>
        <CartView />
    </OrderProvider>
));
