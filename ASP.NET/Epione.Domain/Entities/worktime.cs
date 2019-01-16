namespace Epione.Domain
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    [Table("epione.worktime")]
    public partial class worktime
    {
        public int id { get; set; }

        [StringLength(255)]
        public string day { get; set; }

        public TimeSpan? endTime { get; set; }

        public TimeSpan? startTime { get; set; }

        public int? schedule_id { get; set; }

        public virtual schedule schedule { get; set; }
    }
}
