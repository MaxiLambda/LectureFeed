export class LocalStorage
{
  private static prefix = "lecturefeed-";

  public static get(id: string){
    return localStorage.getItem(LocalStorage.prefix+id);
  }

  public static getOrDefault(id: string, defaultValue: string){
    let value = LocalStorage.get(id);
    if(value !== null) return value;
    return defaultValue;
  }

  public static set(id: string, value: string){
    localStorage.setItem(LocalStorage.prefix+id, value);
  }

}
