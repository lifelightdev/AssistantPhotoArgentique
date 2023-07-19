import { Injectable } from '@angular/core';
import { MatPaginatorIntl } from '@angular/material/paginator';

@Injectable({
  providedIn: 'root'
})
export class paginationPersonnalise extends MatPaginatorIntl {

  // @ts-ignore
  itemsPerPageLabel = 'Élément par page';
  // @ts-ignore
  nextPageLabel = 'Page suivante';
  // @ts-ignore
  previousPageLabel = 'Page précédente';
  // @ts-ignore
  firstPageLabel = 'La première page';
  // @ts-ignore
  lastPageLabel = 'Dernière page';

  // @ts-ignore
  getRangeLabel = (page: number, pageSize: number, length: number) => {
    if (length === 0 || pageSize === 0) {
      return '0 de ' + length;
    }
    length = Math.max(length, 0);
    const startIndex = page * pageSize;
    // If the start index exceeds the list length, do not try and fix the end index to the end.
    const endIndex = startIndex < length ?
      Math.min(startIndex + pageSize, length) :
      startIndex + pageSize;
    return startIndex + 1 + ' - ' + endIndex + ' Sur ' + length;
  };

}
