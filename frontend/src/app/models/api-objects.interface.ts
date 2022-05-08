export interface Sector {
  id: string;
  name: string;
  children?: Sector[];
}

export interface Person {
  name: string;
  termsAccepted: boolean;
  selectedSectors: string[];
}
