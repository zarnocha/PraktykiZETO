/* tslint:disable */
/* eslint-disable */
// Generated using typescript-generator version 3.2.1263 on 2023-09-18 12:12:24.

declare namespace api {

    interface CarBrandModelDTO {
        brand: string;
        models: string[];
    }

    interface CarDTO {
        id: number;
        brand: string;
        model: string;
        productionYear: number;
        fuelConsumption: number;
        engineCapacity: number;
        drive: Drive;
        seats: number;
        gearbox: Gearbox;
        dayPrice: number;
        description: string;
        picture: string;
    }

    interface CarFilterDTO {
        minHorsepower?: number;
        maxHorsepower?: number;
        brands?: CarBrandModelDTO[];
    }

    interface CarListQueryParamsDTO {
        brand?: string;
        model?: string;
        horsePowerFrom?: number;
        horsePowerTo?: number;
        gearbox?: Gearbox;
        drive?: Drive;
        from?: Date;
        to?: Date;
        available?: boolean;
    }

    interface CarPriceDTO {
        wholePrice: number;
        dayPrice: number;
    }

    interface EditCarDTO {
        brand: string;
        model: string;
        licensePlate: string;
        description: string;
        value: number;
        mileage: number;
        picture: string;
    }

    interface AddRentDTO {
        startTime: Date;
        endTime: Date;
        carId: number;
        userId?: number;
    }

    interface RentCarDTO {
        id: number;
        brand: string;
        model: string;
        picture: string;
    }

    interface RentDTO {
        id: number;
        startTime: Date;
        endTime: Date;
        actualStartTime: Date;
        actualEndTime: Date;
        price: number;
        car: RentCarDTO;
        user: SimpleUserDTO;
    }

    interface RentListQueryParamsDTO {
        from?: Date;
        to?: Date;
        priceFrom?: number;
        priceTo?: number;
        carId?: number;
        userId?: number;
        returned?: boolean;
        isLate?: boolean;
    }

    interface AdminRegisterDTO {
        login: string;
        password: string;
        firstName: string;
        lastName: string;
    }

    interface EditProfileDTO {
        password?: string;
        firstName?: string;
        lastName?: string;
        creditCardNumber?: string;
        creditCardExpDate?: Date;
        cvv?: string;
    }

    interface SimpleUserDTO {
        id: number;
        firstName: string;
        lastName: string;
    }

    interface UserLoginDTO {
        login: string;
        password: string;
    }

    interface UserProfileDTO {
        id: number;
        login: string;
        firstName: string;
        lastName: string;
        creditCardNumber: string;
        creditCardExpDate: Date;
        cvv: string;
    }

    interface UserProfileWithRentsDTO {
        profile: UserProfileDTO;
        rents: RentDTO[];
    }

    interface UserRegisterDTO {
        login: string;
        password: string;
        firstName: string;
        lastName: string;
        creditCardNumber: string;
        creditCardExpDate: Date;
        cvv: string;
    }

    type Drive = "FWD" | "RWD" | "AWD";

    type Gearbox = "MANUAL" | "AUTOMATIC" | "SEMI_AUTOMATIC";

}
